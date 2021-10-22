package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.dto.UtenteGenericoDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class HomeController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("guida")
    public String guida() {
        return "guida";
    }

    // Get registrazione generico
    @GetMapping("registration")
    public String registration(Model model) {
        UtenteGenericoDTO utenteGenericoDTO = new UtenteGenericoDTO();
        model.addAttribute("utentegenericoDTO", utenteGenericoDTO);
        return "registration";
    }

    // Post registrazione generico
    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("utentegenericoDTO") UtenteGenericoDTO request,
                           BindingResult bindingResult,
                           Model model) {
        model.addAttribute("utentegenericoDTO", request);
        if (bindingResult.hasErrors())
            return "registration";

        // Controllo se l'email esiste già nel DB
        boolean flag = false;
        try {
            customUserDetailService.loadUserByUsername(request.getIndirizzoEmail());
        } catch (UsernameNotFoundException e) {
            flag = true;
        }
        if(flag == false)
            return "redirect:/registration?already_taken";

        // Controllo che le password coincidano
        if (request.getPassword().equals(request.getConferma_password()));
        else {
            // throw new CustomException(ErrorMessage.PASSWORD_DOESNT_MATCH);
            return "redirect:/registration?pass_match";
        }

        // Collegamento alla seconda pagina di registrazione
        switch (request.getRuolo()) {
            case "LABORATORIO":
                LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
                model.addAttribute("laboratorioDTO", laboratorioDTO);
                laboratorioDTO.setIndirizzoEmail(request.getIndirizzoEmail());
                laboratorioDTO.setPassword(request.getPassword());
                laboratorioDTO.setConferma_password(request.getConferma_password());
                laboratorioDTO.setRuolo(request.getRuolo());
                return "laboratorio/registration";
        }

        return null; // TOGLIERE INFINE
    }

    @GetMapping(path = "registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        try {
            return registrationService.confirmToken(token);
        } catch (CustomException e) {
            if(e.getMessage().equals(ErrorMessage.EMAIL_ALREADY_CONFIRMED))
                return "redirect:/login?already_confirmed";
            else if(e.getMessage().equals(ErrorMessage.TOKEN_NOT_FOUND))
                return "redirect:/login?token_exp";
            else // Token expired
                return "redirect:/login?token_exp";
        }
    }
}
