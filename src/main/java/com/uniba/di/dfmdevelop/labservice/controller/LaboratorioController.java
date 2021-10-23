package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("index")
    public String index() {
        log.info("Starting index laboratorio");
        return "laboratorio/index";
    }

    @GetMapping("update")
    public String updateProfile() {
        log.info("Updating profile");
        return "laboratorio/update";
    }

    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                           BindingResult bindingResult,
                           Model model) {
        log.info("Starting registration Laboratorio...");
        model.addAttribute("laboratorioDTO", request);

        if (bindingResult.hasErrors()){
            log.error("Error in Laboratorio registration");
            return "laboratorio/registration";}

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