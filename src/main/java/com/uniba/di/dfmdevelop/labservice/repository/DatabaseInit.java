package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

// Creo 2 Laboratori di test, e attivo gli account.
@Configuration
public class DatabaseInit implements CommandLineRunner {

    private final UtenteGenericoRepository utenteGenericoRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInit(UtenteGenericoRepository utenteGenericoRepository, PasswordEncoder passwordEncoder) {
        this.utenteGenericoRepository = utenteGenericoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        UtenteGenerico utenteGenerico1 = new UtenteGenerico(
                "lab_lecce@labservice.it",
                passwordEncoder.encode("lab123"),
                "LABORATORIO"
        );
        UtenteGenerico utenteGenerico2 = new UtenteGenerico(
                "lab_bari@labservice.it",
                passwordEncoder.encode("lab123"),
                "LABORATORIO"
        );
        utenteGenerico1.setEnabled(true);
        utenteGenerico2.setEnabled(true);
        Laboratorio lab1 = new Laboratorio(
                "Laboratorio Analisi Lecce",
                "0376-8676978",
                "Via E. Fermi, 22",
                "IT07O0300203280722787714851",
                "31805080079",
                utenteGenerico1
        );
        Laboratorio lab2 = new Laboratorio(
                "Laboratorio Analisi Bari",
                "0334-2977722",
                "Via E. Fermi, 22",
                "IT16R0300203280345696191345",
                "76013160171",
                utenteGenerico2
        );
        utenteGenerico1.setLaboratorio(lab1);
        utenteGenerico2.setLaboratorio(lab2);
        utenteGenericoRepository.saveAll(List.of(utenteGenerico1,utenteGenerico2));
    }
}
