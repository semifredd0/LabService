package com.uniba.di.dfmdevelop.labservice.config;

import com.uniba.di.dfmdevelop.labservice.model.*;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Calendario;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Tampone;
import com.uniba.di.dfmdevelop.labservice.repository.*;
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
    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteEsternoRepository utenteEsternoRepository;
    private final LaboratorioTamponeRepository laboratorioTamponeRepository;
    private final CalendarioLaboratorioRepository calendarioLaboratorioRepository;
    private final PasswordEncoder passwordEncoder;

    // Prenotazioni da cittadini
    Prenotazione p1 = new Prenotazione();
    Prenotazione p2 = new Prenotazione();
    Prenotazione p3 = new Prenotazione();
    Prenotazione p4 = new Prenotazione();
    Prenotazione p5 = new Prenotazione();
    Prenotazione p6 = new Prenotazione();
    Prenotazione p7 = new Prenotazione();
    Prenotazione p8 = new Prenotazione();
    // Prenotazioni da medici
    Prenotazione p9 = new Prenotazione();
    Prenotazione p10 = new Prenotazione();
    // Prenotazioni da medici
    Prenotazione p11 = new Prenotazione();
    Prenotazione p12 = new Prenotazione();

    public DatabaseInit(UtenteGenericoRepository utenteGenericoRepository,
                        TamponeRepository tamponeRepository,
                        PrenotazioneRepository prenotazioneRepository,
                        UtenteEsternoRepository utenteEsternoRepository,
                        LaboratorioTamponeRepository laboratorioTamponeRepository,
                        CalendarioLaboratorioRepository calendarioLaboratorioRepository,
                        PasswordEncoder passwordEncoder) {
        this.utenteGenericoRepository = utenteGenericoRepository;
        this.tamponeRepository = tamponeRepository;
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteEsternoRepository = utenteEsternoRepository;
        this.laboratorioTamponeRepository = laboratorioTamponeRepository;
        this.calendarioLaboratorioRepository = calendarioLaboratorioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initLaboratorio();
        initCittadino();
        initMedico();
        initDatore();
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
                "Via Fermi, 22",
                "IT07O0300203280722787714851",
                "31805080079",
                utenteGenerico1
        );
        Laboratorio lab2 = new Laboratorio(
                "Laboratorio Analisi Bari",
                "3342977722",
                "Via Padova, 22",
                "IT16R0300203280345696191345",
                "76013160171",
                utenteGenerico2
        );
        lab1.setLatitudine("40.34818303128154");
        lab1.setLongitudine("18.151186807179084");
        lab2.setLatitudine("41.07719123279765");
        lab2.setLongitudine("16.869093870056368");

        utenteGenerico1.setLaboratorio(lab1);
        utenteGenerico2.setLaboratorio(lab2);

        // Tampone molecolare
        Tampone molecolare = new Tampone();
        molecolare.setId(Long.valueOf(0));
        molecolare.setNome("Test Molecolare");

        // Tampone antigenico
        Tampone antigenico = new Tampone();
        antigenico.setId(Long.valueOf(1));
        antigenico.setNome("Test Antigenico");

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

        // Aggiungo le prenotazioni per i vari laboratori
        p1.setLaboratorioTampone(laboratorioTampone1);
        p1.setDataPrenotazione(LocalDate.of(2021,10,12));
        p2.setLaboratorioTampone(laboratorioTampone2);
        p2.setDataPrenotazione(LocalDate.of(2021,11,12));
        p3.setLaboratorioTampone(laboratorioTampone3);
        p3.setDataPrenotazione(LocalDate.of(2021,11,12));
        p4.setLaboratorioTampone(laboratorioTampone4);
        p4.setDataPrenotazione(LocalDate.of(2021,11,12));
        p5.setLaboratorioTampone(laboratorioTampone5);
        p5.setDataPrenotazione(LocalDate.of(2021,11,12));
        p6.setLaboratorioTampone(laboratorioTampone6);
        p6.setDataPrenotazione(LocalDate.of(2021,11,12));

        // Creo due utenti esterni
        UtenteEsterno ue1 = new UtenteEsterno(
                "Antonio","Costantini", LocalDate.of(2000,1,12),
                "3339898765","CSTNTN00A12E876T");
        UtenteEsterno ue2 = new UtenteEsterno(
                "Carlo","Costantini", LocalDate.of(2000,1,12),
                "3339898765","CSTCRL00A12E876T");

        // Aggiungo due prenotazioni per utenti esterni [Lab1 - Molecolare]
        p7.setUtenteEsterno(ue1);
        p7.setLaboratorioTampone(laboratorioTampone1);
        p7.setDataPrenotazione(LocalDate.of(2021,11,12));
        p8.setUtenteEsterno(ue2);
        p8.setLaboratorioTampone(laboratorioTampone1);
        p8.setDataPrenotazione(LocalDate.of(2021,11,12));

        // Aggiungo due prenotazioni per assistiti [Lab1 - Molecolare]
        p9.setUtenteEsterno(ue1);
        p9.setLaboratorioTampone(laboratorioTampone1);
        p9.setDataPrenotazione(LocalDate.of(2021,11,12));
        p10.setUtenteEsterno(ue2);
        p10.setLaboratorioTampone(laboratorioTampone1);
        p10.setDataPrenotazione(LocalDate.of(2021,11,12));

        /* Aggiungo due prenotazioni per dipendenti [Lab1 - Molecolare]
            In questo caso i dipendenti, ossia UtenteEsterno, vengono
            istanziati e aggiunti alla prenotazione nella funzione initDatore().
        */
        p11.setLaboratorioTampone(laboratorioTampone1);
        p11.setDataPrenotazione(LocalDate.of(2021,11,12));
        p12.setLaboratorioTampone(laboratorioTampone1);
        p12.setDataPrenotazione(LocalDate.of(2021,11,12));

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
        utenteEsternoRepository.saveAll(List.of(ue1,ue2));
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

        // Le 6 prenotazioni sono state fatte dai due cittadini
        p1.setUtenteGenerico(utenteGenerico1);
        p2.setUtenteGenerico(utenteGenerico1);
        p3.setUtenteGenerico(utenteGenerico1);
        p4.setUtenteGenerico(utenteGenerico1);
        p5.setUtenteGenerico(utenteGenerico1);
        p6.setUtenteGenerico(utenteGenerico1);

        // Le due prenotazioni per utenti esterni sono state fatte dai due cittadini
        p7.setUtenteGenerico(utenteGenerico1);
        p8.setUtenteGenerico(utenteGenerico2);

        // Quattro prenotazioni pagate online, altre quattro pagate in sede
        p1.setPagamentoOnline(true);
        p2.setPagamentoOnline(true);
        p3.setPagamentoOnline(true);
        p4.setPagamentoOnline(true);
        p5.setPagamentoOnline(false);
        p6.setPagamentoOnline(false);
        p7.setPagamentoOnline(false);
        p8.setPagamentoOnline(false);

        utenteGenericoRepository.saveAll(List.of(utenteGenerico1,utenteGenerico2));
        prenotazioneRepository.saveAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8));
    }

    private void initMedico() {

        UtenteGenerico utenteGenerico1 = new UtenteGenerico(
                "dottor_costa@labservice.it",
                passwordEncoder.encode("111111"),
                "MEDICO"
        );
        UtenteGenerico utenteGenerico2 = new UtenteGenerico(
                "dottor_brescia@labservice.it",
                passwordEncoder.encode("111111"),
                "MEDICO"
        );
        utenteGenerico1.setEnabled(true);
        utenteGenerico2.setEnabled(true);

        MedicoMedicinaGenerale medico1 = new MedicoMedicinaGenerale();
        medico1.setNome("Matteo");
        medico1.setCognome("Costantini");
        medico1.setDataNascita(LocalDate.of(2000,6,29));
        medico1.setSpecializzazione("Dentista");
        medico1.setIndirizzoStudio("Via Matteotti, 80, Lecce");
        medico1.setNumeroTelefono("3275412098");
        medico1.setUtenteGenerico(utenteGenerico1);

        MedicoMedicinaGenerale medico2 = new MedicoMedicinaGenerale();
        medico2.setNome("Francesco");
        medico2.setCognome("Brescia");
        medico2.setDataNascita(LocalDate.of(2000,6,29));
        medico2.setSpecializzazione("Dermatologo");
        medico2.setIndirizzoStudio("Via Giulio Cesare, 2, Bari");
        medico2.setNumeroTelefono("3265542448");
        medico2.setUtenteGenerico(utenteGenerico2);

        utenteGenerico1.setMedico(medico1);
        utenteGenerico2.setMedico(medico2);

        // Le 2 prenotazioni sono state fatte da medico1
        p9.setUtenteGenerico(utenteGenerico1);
        p10.setUtenteGenerico(utenteGenerico1);

        // Le 2 prenotazioni sono state pagate tutte online
        p9.setPagamentoOnline(true);
        p10.setPagamentoOnline(true);

        utenteGenericoRepository.saveAll(List.of(utenteGenerico1,utenteGenerico2));
        prenotazioneRepository.saveAll(List.of(p9,p10));
    }

    private void initDatore() {
        UtenteGenerico utenteGenerico1 = new UtenteGenerico(
                "ingegnere_costa@labservice.it",
                passwordEncoder.encode("111111"),
                "DATORE"
        );
        UtenteGenerico utenteGenerico2 = new UtenteGenerico(
                "ingegnere_brescia@labservice.it",
                passwordEncoder.encode("111111"),
                "DATORE"
        );
        utenteGenerico1.setEnabled(true);
        utenteGenerico2.setEnabled(true);

        DatoreLavoro datore1 = new DatoreLavoro();
        datore1.setNome("Matteo");
        datore1.setCognome("Costantini");
        datore1.setDataNascita(LocalDate.of(2000,6,29));
        datore1.setNomeAzienda("Syfer SRL");
        datore1.setIndirizzoAzienda("Via Matteotti, 80, Lecce");
        datore1.setNumeroTelefono("3275412098");
        datore1.setUtenteGenerico(utenteGenerico1);

        DatoreLavoro datore2 = new DatoreLavoro();
        datore2.setNome("Francesco");
        datore2.setCognome("Brescia");
        datore2.setDataNascita(LocalDate.of(2000,6,29));
        datore2.setNomeAzienda("Brescia Company");
        datore2.setIndirizzoAzienda("Via Giulio Cesare, 2, Bari");
        datore2.setNumeroTelefono("3265542448");
        datore2.setUtenteGenerico(utenteGenerico2);

        utenteGenerico1.setDatore(datore1);
        utenteGenerico2.setDatore(datore2);

        // Creo due dipendenti per datore1
        UtenteEsterno dipendente1 = new UtenteEsterno(
                "Luca","Manta", LocalDate.of(2000,1,12),
                "3339898765","MNTLCA00A12E876T");
        UtenteEsterno dipendente2 = new UtenteEsterno(
                "Diego","Donadei", LocalDate.of(2000,1,12),
                "3339898765","DNDDGO00A12E876T");
        dipendente1.setDatoreLavoro(datore1);
        dipendente2.setDatoreLavoro(datore1);

        // Le 2 prenotazioni sono state fatte da datore1
        p11.setUtenteGenerico(utenteGenerico1);
        p12.setUtenteGenerico(utenteGenerico1);

        // Le 2 prenotazioni sono state fatte per i 2 dipendenti
        p11.setUtenteEsterno(dipendente1);
        p12.setUtenteEsterno(dipendente2);

        // Le 2 prenotazioni sono state pagate tutte online
        p11.setPagamentoOnline(true);
        p12.setPagamentoOnline(true);

        utenteGenericoRepository.saveAll(List.of(utenteGenerico1,utenteGenerico2));
        utenteEsternoRepository.saveAll(List.of(dipendente1,dipendente2));
        prenotazioneRepository.saveAll(List.of(p11,p12));
    }
}
