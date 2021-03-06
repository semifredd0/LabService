package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.dto.DataDTO;
import com.uniba.di.dfmdevelop.labservice.dto.MedicoDTO;
import com.uniba.di.dfmdevelop.labservice.dto.PrenotazioneCittadinoDTO;
import com.uniba.di.dfmdevelop.labservice.email.EmailSender;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.maps.DistanceSorter;
import com.uniba.di.dfmdevelop.labservice.maps.GeocodeResult;
import com.uniba.di.dfmdevelop.labservice.maps.LaboratorioDistanza;
import com.uniba.di.dfmdevelop.labservice.model.FileDB;
import com.uniba.di.dfmdevelop.labservice.model.MedicoMedicinaGenerale;
import com.uniba.di.dfmdevelop.labservice.model.Prenotazione;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.UtenteEsterno;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import com.uniba.di.dfmdevelop.labservice.paypal.Payment;
import com.uniba.di.dfmdevelop.labservice.repository.*;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import com.uniba.di.dfmdevelop.labservice.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("medico")
@AllArgsConstructor
public class MedicoController {

    private final RegistrationService registrationService;
    private final CustomUserDetailService service;
    private final UtenteGenericoRepository utenteGenericoRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final TamponeRepository tamponeRepository;
    private final LaboratorioTamponeRepository laboratorioTamponeRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteEsternoRepository utenteEsternoRepository;
    private final FileDBRepository fileDBRepository;
    private final GeocodeController geocode;
    private final EmailSender emailSender;

    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("posizione",null);
        return "medico/index";
    }

    @GetMapping("updateProfile")
    public String updateProfile(@AuthenticationPrincipal UtenteGenerico utente, Model model) {
        // Ricarico l'utente dal DB, in quanto possono essere state fatte delle modifiche
        utente = utenteGenericoRepository.getById(utente.getId());
        // Imposto tutti i campi per l'autocompilazione
        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setIndirizzoEmail(utente.getEmail());
        medicoDTO.setPassword(utente.getPassword());
        medicoDTO.setConferma_password(utente.getPassword());
        medicoDTO.setRuolo(utente.getRole());
        medicoDTO.setNome(utente.getMedico().getNome());
        medicoDTO.setCognome(utente.getMedico().getCognome());
        medicoDTO.setDataNascita(utente.getMedico().getDataNascita());
        medicoDTO.setNumeroTelefono(utente.getMedico().getNumeroTelefono());
        medicoDTO.setSpecializzazione(utente.getMedico().getSpecializzazione());
        medicoDTO.setIndirizzoStudio(utente.getMedico().getIndirizzoStudio());
        // Aggiungo il DTO completo al modello
        model.addAttribute("medicoDTO",medicoDTO);
        return "medico/updateProfile";
    }

    @PostMapping("updateProfile")
    public String updateProfile(@Valid @ModelAttribute("medicoDTO") MedicoDTO request,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UtenteGenerico utente,
                                Model model) {

        model.addAttribute("medicoDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in updating profile");
            return "medico/updateProfile";
        }
        // Aggiorna i campi del principal nella sessione corrente
        MedicoMedicinaGenerale medico = new MedicoMedicinaGenerale();
        medico.setNome(request.getNome());
        medico.setCognome(request.getCognome());
        medico.setDataNascita(request.getDataNascita());
        medico.setNumeroTelefono(request.getNumeroTelefono());
        medico.setSpecializzazione(request.getSpecializzazione());
        medico.setIndirizzoStudio(request.getIndirizzoStudio());
        utente.setMedico(medico);
        // Carico le modifiche nel database
        service.updateUser(request,"medicoDTO");
        return "redirect:/medico/updateProfile?success";
    }

    @PostMapping("registration")
    public String register(@Valid @ModelAttribute("medicoDTO") MedicoDTO request,
                           BindingResult bindingResult,
                           Model model) {

        model.addAttribute("medicoDTO", request);
        if (bindingResult.hasErrors()) {
            log.error("Error in medico registration");
            return "medico/registration";
        }
        try {
            log.info("Trying to register...");
            registrationService.register(request, "medicoDTO");
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

    //------------

    @GetMapping("esitoTamponi")
    public String listaPrenotazioni(@AuthenticationPrincipal UtenteGenerico utente,
                                    Model model) {
        List<Prenotazione> lista_prenotazioni = prenotazioneRepository
                .findPrenotazioneByUtenteGenerico(utente);

        model.addAttribute("lista",lista_prenotazioni);
        model.addAttribute("data",new DataDTO());
        return "medico/esitoTamponi";
    }

    @PostMapping("/esitoTamponi")
    public String listaPrenotazioniData(@AuthenticationPrincipal UtenteGenerico utente,
                                        @Valid DataDTO data,
                                        BindingResult bindingResult,
                                        Model model) {
        if(bindingResult.hasErrors()) {
            log.error("Error in lista prenotazioni");
            return "medico/esitoTamponi";
        }

        List<Prenotazione> lista_prenotazioni = prenotazioneRepository
                .findPrenotazioneByUtenteGenerico(utente);

        if(data.getDataCercata() != null) {
            List<Prenotazione> lista_filtered = new ArrayList<>();
            for(Prenotazione prenotazione : lista_prenotazioni) {
                if(prenotazione.getDataPrenotazione().isEqual(data.getDataCercata()))
                    lista_filtered.add(prenotazione);
            }
            lista_prenotazioni = lista_filtered;
            model.addAttribute("lista",lista_prenotazioni);
        }
        else
            model.addAttribute("lista",null);

        model.addAttribute("data",data);
        return "medico/esitoTamponi";
    }

    @GetMapping("/prenotazione/{id}")
    public String prenotazioneSelezionata(@PathVariable("id") Long id, Model model) {
        Prenotazione prenotazione = prenotazioneRepository.getById(id);
        List<FileDB> listDocs = fileDBRepository.findFileDBByPrenotazione(prenotazione);
        model.addAttribute("listDocs",listDocs);
        model.addAttribute("prenotazione",prenotazione);
        return "medico/dettagliPrenotazione";
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

    //------------

    @PostMapping("ricercaLaboratorio")
    public String ricercaLaboratorio(@RequestParam String posizione, Model model) {
        // Trovo latitudine e longitudine della posizione inserita
        GeocodeResult result;
        try {
            result = geocode.getGeocode(posizione);
            if(result.getResults().isEmpty())
                throw new Exception();
        } catch(Exception e) {
            return "redirect:/medico/index?posizione_not_valid";
        }
        String latitudine = result.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude();
        String longitudine = result.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude();

        // Prendo tutti i laboratori dal DB e trovo le distanze
        List<Laboratorio> laboratori = service.getAllLaboratorio();
        List<LaboratorioDistanza> lista = new ArrayList<>();
        for(Laboratorio lab : laboratori) {
            double lat1 = Double.parseDouble(lab.getLatitudine());
            double lon1 = Double.parseDouble(lab.getLongitudine());
            double lat2 = Double.parseDouble(latitudine);
            double lon2 = Double.parseDouble(longitudine);
            LaboratorioDistanza temp = new LaboratorioDistanza(lab,distanceInKm(lat1,lon1,lat2,lon2));
            lista.add(temp);
        }
        // Ordino la lista
        Collections.sort(lista, new DistanceSorter());
        Collections.reverse(lista);

        model.addAttribute("posizione",result.getResults().get(0).getFormattedAddress());
        model.addAttribute("lista",lista);
        return "medico/listaLaboratori";
    }

    @GetMapping("/{id}")
    public String laboratorioSelezionato(@PathVariable("id") Long id, Model model) {
        Laboratorio lab1 = laboratorioRepository.getById(id);
        List<LaboratorioTampone> lista = service.getListTampone(lab1);
        LaboratorioTampone laboratorioTampone = new LaboratorioTampone();
        laboratorioTampone.setLaboratorio(lab1);

        model.addAttribute("laboratorio",lab1);
        model.addAttribute("tamponi",lista);
        model.addAttribute("tampone",laboratorioTampone);
        return "medico/indexForUtente";
    }

    @PostMapping("/bookTampone")
    public String prenotaTampone(@ModelAttribute("tampone") LaboratorioTampone tampone,
                                 Model model) {
        // LabID: tampone.getLaboratorio().getId();
        // TamponeID: tampone.getTampone().getId();
        // LocalDate date = LocalDate.now();
        
        PrenotazioneCittadinoDTO prenotazioneCittadinoDTO = new PrenotazioneCittadinoDTO();
        prenotazioneCittadinoDTO.setIdLaboratorio(tampone.getLaboratorio().getId());
        prenotazioneCittadinoDTO.setIdTampone(tampone.getTampone().getId());

        model.addAttribute("prenotazioneDTO",prenotazioneCittadinoDTO);
        return "medico/bookTampone";
    }

    @PostMapping("/payment")
    public String prenotaTampone(@Valid @ModelAttribute("prenotazioneDTO") PrenotazioneCittadinoDTO prenotazione,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal UtenteGenerico utente,
                                 Model model) throws CustomException {

        if(bindingResult.hasErrors()) {
            log.error("Error in booking tampone");
            return "medico/bookTampone";
        }

        Prenotazione prenotazione_obj = new Prenotazione();
        LaboratorioTampone laboratorioTampone = laboratorioTamponeRepository.getItem(
                laboratorioRepository.getById(prenotazione.getIdLaboratorio()),
                tamponeRepository.getById(prenotazione.getIdTampone())
        );

        // Un assistito ?? come un utente esterno
        UtenteEsterno utenteEsterno = new UtenteEsterno();
        utenteEsterno.setNome(prenotazione.getNome());
        utenteEsterno.setCognome(prenotazione.getCognome());
        utenteEsterno.setDataNascita(prenotazione.getDataNascita());
        utenteEsterno.setCodFiscale(prenotazione.getCodiceFiscale());
        utenteEsterno.setNumeroTelefono(prenotazione.getNumeroTelefono());

        prenotazione_obj.setUtenteGenerico(utente);
        prenotazione_obj.setLaboratorioTampone(laboratorioTampone);
        prenotazione_obj.setDataPrenotazione(prenotazione.getDataPrenotazione());
        prenotazione_obj.setUtenteEsterno(utenteEsterno);
        utenteEsternoRepository.save(utenteEsterno);

        prenotazione_obj.setPagamentoOnline(prenotazione.isPagamento());
        if(!prenotazione.isPagamento()) {
            // Pagamento in sede
            String nomeLab = laboratorioRepository.getById(prenotazione.getIdLaboratorio()).getNome();
            emailSender.send(
                    prenotazione.getIndirizzoEmail(),
                    prenotazioneInSede(prenotazione.getIndirizzoEmail(),prenotazione.getDataPrenotazione().toString(),nomeLab,
                            String.format("%.2f",prenotazione_obj.getLaboratorioTampone().getPrezzo())),
                    "Prenotazione tampone");

            prenotazioneRepository.save(prenotazione_obj);
            return "medico/payment/success";
        }
        else {
            // Pagamento online
            String nomeLab = laboratorioRepository.getById(prenotazione.getIdLaboratorio()).getNome();
            emailSender.send(
                    prenotazione.getIndirizzoEmail(),
                    prenotazioneOnline(prenotazione.getIndirizzoEmail(),prenotazione.getDataPrenotazione().toString(),nomeLab),
                    "Prenotazione tampone");

            prenotazioneRepository.save(prenotazione_obj);

            Payment payment = new Payment();
            payment.setPrice(prenotazione_obj.getLaboratorioTampone().getPrezzo());
            model.addAttribute("payment",payment);
            model.addAttribute("prenotazione",prenotazione);
            return "medico/payment/index";
        }
    }

    private double distanceInKm(double lat1, double lon1, double lat2, double lon2) {
        double raggioTerra = 6371;
        double lat = deg2rad(lat2-lat1);
        double lon = deg2rad(lon2-lon1);
        double a = Math.sin(lat/2) * Math.sin(lat/2) + Math.cos(deg2rad(lat1)) *
                Math.cos(deg2rad(lat2)) * Math.sin(lon/2) * Math.sin(lon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = raggioTerra * c;
        return distance;
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    private String prenotazioneOnline(String name, String date, String lab) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Prenotazione tampone</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Ciao " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" +
                "            La sua prenotazione ?? avvenuta con successo!<br>" +
                "            Si presenti il giorno " + date +" per effettuare un tampone presso il laboratorio: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " + lab + " </p></blockquote>\n<p>A presto!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    private String prenotazioneInSede(String name, String date, String lab, String price) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Prenotazione tampone</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Ciao " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" +
                "            La sua prenotazione ?? avvenuta con successo!<br>" +
                "            Si presenti il giorno " + date +" per effettuare un tampone presso il laboratorio: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " + lab + " </p></blockquote>\n" +
                "            Prima di effettuare il tampone bisogna effettuare il pagamento in contanti.<br>Prezzo del tampone prenotato: ??? " + price + "\n<p>A presto!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}