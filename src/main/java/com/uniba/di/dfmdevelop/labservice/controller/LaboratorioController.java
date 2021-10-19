package com.uniba.di.dfmdevelop.labservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("laboratorio")
public class LaboratorioController {

    @GetMapping("index")
    public String index() { return "laboratorio/index"; }
}
