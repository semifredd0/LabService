package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
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

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    // REGISTRATION -----------------

    @GetMapping("registration")
    public String registration(Model model) {
        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        model.addAttribute("laboratorioDTO",laboratorioDTO);
        return "registration";
    }

    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                           BindingResult bindingResult,
                           Model model) {
        // System.out.println(request);
        model.addAttribute("laboratorioDTO", request);
        if(bindingResult.hasErrors())
            return "registration";

        registrationService.register(request);
        return "redirect:/registration?success";
    }

    @GetMapping(path = "registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    // -----------------
}
