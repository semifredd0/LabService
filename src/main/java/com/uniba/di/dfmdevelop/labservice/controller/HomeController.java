package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.dto.UtenteGenericoDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping("index")
    public String index() {
        log.info("Starting index home");
        return "index";
    }

    @GetMapping("login")
    public String login() {
        log.info("Starting login process");
        return "login";
    }

    @GetMapping("guida")
    public String guida() {
        log.info("Starting guide");
        return "guida";
    }

    // Get registrazione generico
    @GetMapping("registration")
    public String registration(Model model) {
        log.info("Starting registration process getmapping");
        UtenteGenericoDTO utenteGenericoDTO = new UtenteGenericoDTO();
        model.addAttribute("utentegenericoDTO", utenteGenericoDTO);
        return "registration";
    }

    // Post registrazione generico
    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("utentegenericoDTO") UtenteGenericoDTO request,
                           BindingResult bindingResult,
                           Model model) {
        log.info("Starting registration process postmapping");
        model.addAttribute("utentegenericoDTO", request);
        if (bindingResult.hasErrors()){
            log.error("Error while registering");
            return "registration";

        }

        // Controllo se l'email esiste già nel DB
        boolean flag = false;
        try {
            log.info("Checking for email presence");
            customUserDetailService.loadUserByUsername(request.getIndirizzoEmail());
        } catch (UsernameNotFoundException e) {
            flag = true;
        }
        if(flag == false) {
            log.error("Mail already taken");
            return "redirect:/registration?already_taken";
        }

        // Controllo che le password coincidano
        if (request.getPassword().equals(request.getConferma_password()));
        else {
            // throw new CustomException(ErrorMessage.PASSWORD_DOESNT_MATCH);
            log.error("Passwords doesn't match");
            return "redirect:/registration?pass_match";
        }

        // Collegamento alla seconda pagina di registrazione
        switch (request.getRuolo()) {
            case "LABORATORIO":
                log.info("Connecting to the second page of registration");
                LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
                // Aggiungo le info di UtenteGenericoDTO al modello
                laboratorioDTO.setIndirizzoEmail(request.getIndirizzoEmail());
                laboratorioDTO.setPassword(request.getPassword());
                laboratorioDTO.setConferma_password(request.getConferma_password());
                laboratorioDTO.setRuolo(request.getRuolo());
                model.addAttribute("laboratorioDTO", laboratorioDTO);
                return "laboratorio/registration";
        }

        return null; // TOGLIERE INFINE
    }

    @GetMapping(path = "registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        try {
            log.info("Registration accepted");
            return registrationService.confirmToken(token);
        } catch (CustomException e) {
            if(e.getMessage().equals(ErrorMessage.EMAIL_ALREADY_CONFIRMED)) {
                log.error("Email already confirmed");
                return "redirect:/login?already_confirmed";
            }
            else if(e.getMessage().equals(ErrorMessage.TOKEN_NOT_FOUND)) {
                log.error("Token not found");
                return "redirect:/login?token_exp";
            }
            else { // Token expired
                log.error("Token expired");
                return "redirect:/login?token_exp";
            }
        }
    }
}
