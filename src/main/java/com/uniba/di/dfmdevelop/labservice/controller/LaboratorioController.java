package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("laboratorio")
@AllArgsConstructor
public class LaboratorioController {

    private final RegistrationService registrationService;

    @GetMapping("index")
    public String index() {
        return "laboratorio/index";
    }

    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                           BindingResult bindingResult,
                           Model model) {
        model.addAttribute("laboratorioDTO", request);
        if (bindingResult.hasErrors())
            return "laboratorio/registration";

        try {
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