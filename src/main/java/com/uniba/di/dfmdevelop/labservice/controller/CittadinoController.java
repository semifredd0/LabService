package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.CittadinoDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.maps.DistanceSorter;
import com.uniba.di.dfmdevelop.labservice.maps.GeocodeResult;
import com.uniba.di.dfmdevelop.labservice.maps.LaboratorioDistanza;
import com.uniba.di.dfmdevelop.labservice.model.Cittadino;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("cittadino")
@AllArgsConstructor
public class CittadinoController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService service;
    private final UtenteGenericoRepository utenteGenericoRepository;
    private final GeocodeController geocode;

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("posizione",null);
        return "cittadino/index";
    }

    @GetMapping("updateProfile")
    public String updateProfile(@AuthenticationPrincipal UtenteGenerico utente, Model model) {
        // Ricarico l'utente dal DB, in quanto possono essere state fatte delle modifiche
        utente = utenteGenericoRepository.getById(utente.getId());
        // Imposto tutti i campi per l'autocompilazione
        CittadinoDTO cittadinoDTO = new CittadinoDTO();
        cittadinoDTO.setIndirizzoEmail(utente.getEmail());
        cittadinoDTO.setPassword(utente.getPassword());
        cittadinoDTO.setConferma_password(utente.getPassword());
        cittadinoDTO.setRuolo(utente.getRole());
        cittadinoDTO.setNome(utente.getCittadino().getNome());
        cittadinoDTO.setCognome(utente.getCittadino().getCognome());
        cittadinoDTO.setDataNascita(utente.getCittadino().getDataNascita());
        cittadinoDTO.setNumeroTelefono(utente.getCittadino().getNumeroTelefono());
        cittadinoDTO.setCodiceFiscale(utente.getCittadino().getCodFiscale());
        // Aggiungo il DTO completo al modello
        model.addAttribute("cittadinoDTO",cittadinoDTO);
        return "cittadino/updateProfile";
    }

    @PostMapping("updateProfile")
    public String updateProfile(@Valid @ModelAttribute("cittadinoDTO") CittadinoDTO request,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UtenteGenerico utente,
                                Model model) {

        model.addAttribute("cittadinoDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in updating profile");
            return "cittadino/updateProfile";
        }
        // Aggiorna i campi del principal nella sessione corrente
        Cittadino cittadino = new Cittadino();
        cittadino.setNome(request.getNome());
        cittadino.setCognome(request.getCognome());
        cittadino.setDataNascita(request.getDataNascita());
        cittadino.setNumeroTelefono(request.getNumeroTelefono());
        cittadino.setCodFiscale(request.getCodiceFiscale());
        utente.setCittadino(cittadino);
        // Carico le modifiche nel database
        service.updateUser(request,"cittadinoDTO");
        return "redirect:/cittadino/updateProfile?success";
    }

    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("cittadinoDTO") CittadinoDTO request,
                           BindingResult bindingResult,
                           Model model) {

        model.addAttribute("cittadinoDTO", request);
        System.out.println();
        if (bindingResult.hasErrors()) {
            log.error("Error in cittadino registration");
            return "cittadino/registration";
        }
        try {
            log.info("Trying to register...");
            registrationService.register(request, "cittadinoDTO");
        } catch (CustomException e) {
            switch (e.getMessage()) {
                case ErrorMessage.EMAIL_ALREADY_TAKEN:
                    return "redirect:/registration?already_taken";
                case ErrorMessage.EMAIL_FAIL_SEND:
                    return "redirect:/registration?fail_send";
            }
        }
        // Successo
        return "redirect:/login?success";
    }

    //------------

    @PostMapping("ricercaLaboratorio")
    public String ricercaLaboratorio(@RequestParam String posizione, Model model) {
        // Trovo latitudine e longitudine della posizione inserita
        GeocodeResult result;
        try {
            result = geocode.getGeocode(posizione);
            if(result.getResults().isEmpty())
                throw new Exception();
        } catch(Exception e) {
            return "redirect:/cittadino/index?posizione_not_valid";
        }
        String latitudine = result.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude();
        String longitudine = result.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude();

        // Prendo tutti i laboratori dal DB e trovo le distanze
        List<Laboratorio> laboratori = service.getAllLaboratorio();
        List<LaboratorioDistanza> lista = new ArrayList<>();
        for(Laboratorio lab : laboratori) {
            double lat1 = Double.parseDouble(lab.getLatitudine());
            double lon1 = Double.parseDouble(lab.getLongitudine());
            double lat2 = Double.parseDouble(latitudine);
            double lon2 = Double.parseDouble(longitudine);
            LaboratorioDistanza temp = new LaboratorioDistanza(lab,distanceInKm(lat1,lon1,lat2,lon2));
            lista.add(temp);
        }
        // Ordino la lista
        Collections.sort(lista, new DistanceSorter());
        Collections.reverse(lista);

        model.addAttribute("posizione",result.getResults().get(0).getFormattedAddress());
        model.addAttribute("lista",lista);
        return "cittadino/listaLaboratori";
    }

    private double distanceInKm(double lat1, double lon1, double lat2, double lon2) {
        double raggioTerra = 6371;
        double lat = deg2rad(lat2-lat1);
        double lon = deg2rad(lon2-lon1);
        double a = Math.sin(lat/2) * Math.sin(lat/2) + Math.cos(deg2rad(lat1)) *
                Math.cos(deg2rad(lat2)) * Math.sin(lon/2) * Math.sin(lon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = raggioTerra * c;
        return distance;
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}