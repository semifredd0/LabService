package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.DataDTO;
import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.dto.PrenotazioneListaDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.model.FileDB;
import com.uniba.di.dfmdevelop.labservice.model.Prenotazione;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Calendario;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.GiornoLavorativo;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.repository.FileDBRepository;
import com.uniba.di.dfmdevelop.labservice.repository.PrenotazioneRepository;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("laboratorio")
@AllArgsConstructor
public class LaboratorioController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService service;
    private final UtenteGenericoRepository utenteGenericoRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final FileDBRepository fileDBRepository;

    @GetMapping("index")
    public String index() {
        return "laboratorio/index";
    }

    @GetMapping("updateProfile")
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
        return "laboratorio/updateProfile";
    }

    @PostMapping("updateProfile")
    public String updateProfile(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UtenteGenerico utente,
                                Model model) {

        model.addAttribute("laboratorioDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in updating profile");
            return "laboratorio/updateProfile";
        }
        // Aggiorna i campi del principal nella sessione corrente
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome(request.getNomeLaboratorio());
        laboratorio.setIndirizzo(request.getIndirizzoStradale());
        laboratorio.setTelefono(request.getNumeroTelefono());
        laboratorio.setIBAN(request.getCodiceIban());
        laboratorio.setPartitaIVA(request.getPartitaIva());
        laboratorio.setCalendario(utente.getLaboratorio().getCalendario());
        utente.setLaboratorio(laboratorio);
        // Carico le modifiche nel database
        service.updateUser(request,"laboratorioDTO");
        return "redirect:/laboratorio/updateProfile?success";
    }

    @GetMapping("updateCalendar")
    public String updateCalendar(@AuthenticationPrincipal UtenteGenerico utente, Model model) {
        log.info("Get(updateCalendar)");
        // Ricarico l'utente dal DB, in quanto possono essere state fatte delle modifiche
        utente = utenteGenericoRepository.getById(utente.getId());
        // Passo tutti i campi obbligatori al DTO
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
        return "laboratorio/updateCalendar";
    }

    @PostMapping("updateCalendar")
    public String updateCalendar(@Valid @ModelAttribute("laboratorioDTO") LaboratorioDTO request,
                                @AuthenticationPrincipal UtenteGenerico utente,
                                BindingResult bindingResult,
                                Model model) {
        log.info("Post(updateCalendar)");
        model.addAttribute("laboratorioDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in updating calendar");
            return "laboratorio/updateCalendar";
        }
        // Aggiorna i campi del principal nella sessione corrente
        Calendario calendario = new Calendario();
        if(request.isLunedi()) {
            GiornoLavorativo lunedi = new GiornoLavorativo(request.getOrario_lunedi().getAperturaMattina(),
                    request.getOrario_lunedi().getChiusuraMattina(),
                    request.getOrario_lunedi().getAperturaPomeriggio(),
                    request.getOrario_lunedi().getChiusuraPomeriggio());
            calendario.setLunedi(lunedi);
            lunedi.setLunedi(calendario);
        }
        if(request.isMartedi()) {
            GiornoLavorativo martedi = new GiornoLavorativo(request.getOrario_martedi().getAperturaMattina(),
                    request.getOrario_martedi().getChiusuraMattina(),
                    request.getOrario_martedi().getAperturaPomeriggio(),
                    request.getOrario_martedi().getChiusuraPomeriggio());
            calendario.setMartedi(martedi);
            martedi.setMartedi(calendario);
        }
        if(request.isMercoledi()) {
            GiornoLavorativo mercoledi = new GiornoLavorativo(request.getOrario_mercoledi().getAperturaMattina(),
                    request.getOrario_mercoledi().getChiusuraMattina(),
                    request.getOrario_mercoledi().getAperturaPomeriggio(),
                    request.getOrario_mercoledi().getChiusuraPomeriggio());
            calendario.setMercoledi(mercoledi);
            mercoledi.setMercoledi(calendario);
        }
        if(request.isGiovedi()) {
            GiornoLavorativo giovedi = new GiornoLavorativo(request.getOrario_giovedi().getAperturaMattina(),
                    request.getOrario_giovedi().getChiusuraMattina(),
                    request.getOrario_giovedi().getAperturaPomeriggio(),
                    request.getOrario_giovedi().getChiusuraPomeriggio());
            calendario.setGiovedi(giovedi);
            giovedi.setGiovedi(calendario);
        }
        if(request.isVenerdi()) {
            GiornoLavorativo venerdi = new GiornoLavorativo(request.getOrario_venerdi().getAperturaMattina(),
                    request.getOrario_venerdi().getChiusuraMattina(),
                    request.getOrario_venerdi().getAperturaPomeriggio(),
                    request.getOrario_venerdi().getChiusuraPomeriggio());
            calendario.setVenerdi(venerdi);
            venerdi.setVenerdi(calendario);
        }
        if(request.isSabato()) {
            GiornoLavorativo sabato = new GiornoLavorativo(request.getOrario_sabato().getAperturaMattina(),
                    request.getOrario_sabato().getChiusuraMattina(),
                    request.getOrario_sabato().getAperturaPomeriggio(),
                    request.getOrario_sabato().getChiusuraPomeriggio());
            calendario.setSabato(sabato);
            sabato.setSabato(calendario);
        }
        if(request.isDomenica()) {
            GiornoLavorativo domenica = new GiornoLavorativo(request.getOrario_domenica().getAperturaMattina(),
                    request.getOrario_domenica().getChiusuraMattina(),
                    request.getOrario_domenica().getAperturaPomeriggio(),
                    request.getOrario_domenica().getChiusuraPomeriggio());
            calendario.setDomenica(domenica);
            domenica.setDomenica(calendario);
        }
        utente.getLaboratorio().setCalendario(calendario);
        // Carico le modifiche nel database
        // Passo direttamente il principal e lo sovrascrive
        service.updateCalendar(utente);
        return "redirect:/laboratorio/updateCalendar?success";
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
                case ErrorMessage.ADDRESS_NOT_VALID:
                    return "redirect:/registration?address_not_valid";
            }
        }
        // Successo
        return "redirect:/login?success";
    }

    @GetMapping("lista")
    public String listaPrenotazioni(@AuthenticationPrincipal UtenteGenerico utente,
                                    Model model) {
        List<Prenotazione> lista_prenotazioni = prenotazioneRepository
                .findPrenotazioneByLaboratorioTampone_Laboratorio(utente.getLaboratorio());
        List<PrenotazioneListaDTO> lista = new ArrayList<>();

        for (Prenotazione prenotazione : lista_prenotazioni) {
            PrenotazioneListaDTO temp = new PrenotazioneListaDTO();
            temp.setPrenotazione(prenotazione);
            if (prenotazione.getUtenteEsterno() == null)
                temp.setCodiceFiscale(prenotazione.getUtenteGenerico().getCittadino().getCodFiscale());
            else
                temp.setCodiceFiscale(prenotazione.getUtenteEsterno().getCodFiscale());
            lista.add(temp);
        }

        model.addAttribute("lista",lista);
        model.addAttribute("data",new DataDTO());
        return "laboratorio/listaPrenotazioni";
    }

    @PostMapping("/lista")
    public String listaPrenotazioniData(@AuthenticationPrincipal UtenteGenerico utente,
                                        @Valid DataDTO data,
                                        BindingResult bindingResult,
                                        Model model) {
        if (bindingResult.hasErrors()) {
            log.error("Error in lista prenotazioni");
            return "laboratorio/listaPrenotazioni";
        }

        List<Prenotazione> lista_prenotazioni = prenotazioneRepository
                .findPrenotazioneByLaboratorioTampone_Laboratorio(utente.getLaboratorio());
        List<PrenotazioneListaDTO> lista = new ArrayList<>();

        for (Prenotazione prenotazione : lista_prenotazioni) {
            PrenotazioneListaDTO temp = new PrenotazioneListaDTO();
            temp.setPrenotazione(prenotazione);
            if (prenotazione.getUtenteEsterno() == null)
                temp.setCodiceFiscale(prenotazione.getUtenteGenerico().getCittadino().getCodFiscale());
            else
                temp.setCodiceFiscale(prenotazione.getUtenteEsterno().getCodFiscale());
            lista.add(temp);
        }

        if(data.getDataCercata() != null) {
            List<PrenotazioneListaDTO> lista_filtered = new ArrayList<>();
            for(PrenotazioneListaDTO prenotazione : lista) {
                if(prenotazione.getPrenotazione().getDataPrenotazione().isEqual(data.getDataCercata()))
                    lista_filtered.add(prenotazione);
            }
            lista = lista_filtered;
            model.addAttribute("lista",lista);
        }
        else
            model.addAttribute("lista",null);

        model.addAttribute("data",data);
        return "laboratorio/listaPrenotazioni";
    }

    @GetMapping("/{id}")
    public String prenotazioneSelezionata(@PathVariable("id") Long id, Model model) {
        Prenotazione prenotazione = prenotazioneRepository.getById(id);
        List<FileDB> listDocs = fileDBRepository.findFileDBByPrenotazione(prenotazione);
        model.addAttribute("listDocs",listDocs);
        model.addAttribute("prenotazione",prenotazione);
        return "laboratorio/dettagliPrenotazione";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document") MultipartFile file,
                             @Valid Prenotazione prenotazione,
                             RedirectAttributes attributes) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB();
        fileDB.setName(filename);
        fileDB.setData(file.getBytes());
        fileDB.setSize(file.getSize());

        Prenotazione prenotazione_full = prenotazioneRepository.getById(prenotazione.getId());
        fileDB.setPrenotazione(prenotazione_full);
        fileDBRepository.save(fileDB);

        attributes.addFlashAttribute("message","File caricato correttamente!");
        return "redirect:/laboratorio/"+prenotazione.getId();
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") String id, HttpServletResponse response) throws Exception {
        Optional<FileDB> result = fileDBRepository.findById(id);
        if(!result.isPresent()) {
            throw new Exception("Documento non trovato!");
        }

        FileDB document = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getName();
        response.setHeader(headerKey,headerValue);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getData());
        outputStream.close();
    }
}