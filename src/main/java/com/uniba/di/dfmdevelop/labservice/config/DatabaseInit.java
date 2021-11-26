package com.uniba.di.dfmdevelop.labservice.config;

import com.uniba.di.dfmdevelop.labservice.model.Cittadino;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Calendario;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Tampone;
import com.uniba.di.dfmdevelop.labservice.repository.CalendarioLaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioTamponeRepository;
import com.uniba.di.dfmdevelop.labservice.repository.TamponeRepository;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

// Creo 2 Laboratori e attivo gli account.
// Creo 3 Tamponi e gli aggiungo tutti ai Laboratori.
@Configuration
public class DatabaseInit implements CommandLineRunner {

    private final UtenteGenericoRepository utenteGenericoRepository;
    private final TamponeRepository tamponeRepository;
    private final LaboratorioTamponeRepository laboratorioTamponeRepository;
    private final CalendarioLaboratorioRepository calendarioLaboratorioRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInit(UtenteGenericoRepository utenteGenericoRepository,
                        TamponeRepository tamponeRepository,
                        LaboratorioTamponeRepository laboratorioTamponeRepository,
                        CalendarioLaboratorioRepository calendarioLaboratorioRepository,
                        PasswordEncoder passwordEncoder) {
        this.utenteGenericoRepository = utenteGenericoRepository;
        this.tamponeRepository = tamponeRepository;
        this.laboratorioTamponeRepository = laboratorioTamponeRepository;
        this.calendarioLaboratorioRepository = calendarioLaboratorioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initLaboratorio();
        initCittadino();
    }

    private void initCittadino() {

        UtenteGenerico utenteGenerico1 = new UtenteGenerico(
                "matteo.costa@labservice.it",
                passwordEncoder.encode("111111"),
                "CITTADINO"
        );
        UtenteGenerico utenteGenerico2 = new UtenteGenerico(
                "francesco.brescia@labservice.it",
                passwordEncoder.encode("111111"),
                "CITTADINO"
        );
        utenteGenerico1.setEnabled(true);
        utenteGenerico2.setEnabled(true);

        Cittadino cittadino1 = new Cittadino(
                "Matteo","Costantini", LocalDate.of(2000,1,12),
                "3339898765","CSTMTT00A12E876T",utenteGenerico1
        );
        Cittadino cittadino2 = new Cittadino(
                "Francesco","Brescia", LocalDate.of(2000,6,29),
                "3261288654","FRNBRS00F29K012U",utenteGenerico2
        );
        utenteGenerico1.setCittadino(cittadino1);
        utenteGenerico2.setCittadino(cittadino2);
        utenteGenericoRepository.saveAll(List.of(utenteGenerico1,utenteGenerico2));
    }

    private void initLaboratorio() {

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
                "3768676978",
                "Via E. Fermi, 22",
                "IT07O0300203280722787714851",
                "31805080079",
                utenteGenerico1
        );
        Laboratorio lab2 = new Laboratorio(
                "Laboratorio Analisi Bari",
                "3342977722",
                "Via E. Fermi, 22",
                "IT16R0300203280345696191345",
                "76013160171",
                utenteGenerico2
        );

        utenteGenerico1.setLaboratorio(lab1);
        utenteGenerico2.setLaboratorio(lab2);

        // Tampone molecolare
        Tampone molecolare = new Tampone();
        molecolare.setId(Long.valueOf(0));
        molecolare.setNome("Test Molecolare");

        // Tampone antigenico
        Tampone antigenico = new Tampone();
        antigenico.setId(Long.valueOf(1));
        antigenico.setNome("Test Antigenico (Rapido)");

        // Tampone sierologico
        Tampone sierologico = new Tampone();
        sierologico.setId(Long.valueOf(2));
        sierologico.setNome("Test Sierologico");

        tamponeRepository.saveAll(List.of(molecolare,antigenico,sierologico));

        // Aggiunge i tamponi ai vari laboratori
        LaboratorioTampone laboratorioTampone1 = new LaboratorioTampone(
                lab1,molecolare,20.00);
        LaboratorioTampone laboratorioTampone2 = new LaboratorioTampone(
                lab2,molecolare,22.00);
        LaboratorioTampone laboratorioTampone3 = new LaboratorioTampone(
                lab1,antigenico,10.00);
        LaboratorioTampone laboratorioTampone4 = new LaboratorioTampone(
                lab2,antigenico,12.00);
        LaboratorioTampone laboratorioTampone5 = new LaboratorioTampone(
                lab1,sierologico,30.00);
        LaboratorioTampone laboratorioTampone6 = new LaboratorioTampone(
                lab2,sierologico,32.00);

        // Aggiungo un calendario vuoto ai vari laboratori
        Calendario cal1 = new Calendario();
        Calendario cal2 = new Calendario();
        lab1.setCalendario(cal1);
        cal1.setLaboratorio(lab1);
        lab2.setCalendario(cal2);
        cal2.setLaboratorio(lab2);

        utenteGenericoRepository.saveAll(List.of(utenteGenerico1,utenteGenerico2));
        calendarioLaboratorioRepository.saveAll(List.of(cal1,cal2));
        laboratorioTamponeRepository.saveAll(List.of(
                laboratorioTampone1,laboratorioTampone2,
                laboratorioTampone3,laboratorioTampone4,
                laboratorioTampone5,laboratorioTampone6));
    }
}
