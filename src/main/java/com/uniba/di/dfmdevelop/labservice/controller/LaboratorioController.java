package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("laboratorio")
@AllArgsConstructor
public class LaboratorioController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService service;
    private final UtenteGenericoRepository utenteGenericoRepository;

    @GetMapping("index")
    public String index() {
        return "laboratorio/index";
    }

    @GetMapping("update")
    public String updateProfile(@AuthenticationPrincipal UtenteGenerico utente, Model model) {
        // Ricarico l'utente dal DB, in quanto possono essere state fatte delle modifiche
        utente = utenteGenericoRepository.getById(utente.getId());
        // Imposto tutti i campi per l'autocompilazione
        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setIndirizzoEmail(utente.getEmail());
        laboratorioDTO.setPassword(utente.getPassword());
        laboratorioDTO.setConferma_password(utente.getPassword());
        laboratorioDTO.setRuolo(utente.getRole());
        laboratorioDTO.setNomeLaboratorio(utente.getLaboratorio().getNome());
        laboratorioDTO.setNumeroTelefono(utente.getLaboratorio().getTelefono());
        laboratorioDTO.setIndirizzoStradale(utente.getLaboratorio().getIndirizzo());
        laboratorioDTO.setCodiceIban(utente.getLaboratorio().getIBAN());
        laboratorioDTO.setPartitaIva(utente.getLaboratorio().getPartitaIVA());
        // Aggiungo il DTO completo al modello
        model.addAttribute("laboratorioDTO",laboratorioDTO);
        return "laboratorio/update";
    }

    @PostMapping("update")
    public String updateProfile(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                           @AuthenticationPrincipal UtenteGenerico utente,
                           BindingResult bindingResult,
                           Model model) {

        model.addAttribute("laboratorioDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in updating laboratorio");
            return "laboratorio/update";
        }
        // Aggiorna i campi del principal nella sessione corrente
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome(request.getNomeLaboratorio());
        laboratorio.setIndirizzo(request.getIndirizzoStradale());
        laboratorio.setTelefono(request.getNumeroTelefono());
        laboratorio.setIBAN(request.getCodiceIban());
        laboratorio.setPartitaIVA(request.getPartitaIva());
        utente.setLaboratorio(laboratorio);
        // Carico le modifiche nel database
        service.updateUser(request,"laboratorioDTO");
        return "redirect:/laboratorio/update?success";
    }

    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                           BindingResult bindingResult,
                           Model model) {

        model.addAttribute("laboratorioDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in laboratorio registration");
            return "laboratorio/registration";
        }
        try {
            log.info("Trying to register...");
            registrationService.register(request, "laboratorioDTO");
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