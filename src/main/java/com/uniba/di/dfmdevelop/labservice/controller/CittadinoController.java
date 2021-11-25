package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.CittadinoDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.model.Cittadino;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("cittadino")
@AllArgsConstructor
public class CittadinoController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService service;
    private final UtenteGenericoRepository utenteGenericoRepository;

    @GetMapping("index")
    public String index() {
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
}