package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.dto.UtenteGenericoDTO;
import com.uniba.di.dfmdevelop.labservice.email.EmailSender;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.model.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.*;
import com.uniba.di.dfmdevelop.labservice.repository.CalendarioLaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioTamponeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomUserDetailService userDetailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final CalendarioLaboratorioRepository calendarioLaboratorioRepository;
    private final LaboratorioTamponeRepository laboratorioTamponeRepository;
    private final EmailSender emailSender;

    public String register(UtenteGenericoDTO request, String role) throws CustomException {

        /* -- Aggiunta la validazione direttamente nel DTO con @Email --
            boolean isValidEmail = emailValidator.
                    test(request.getIndirizzoEmail());
            if (!isValidEmail)
                throw new CustomException(ErrorMessage.EMAIL_NOT_VALID);
         */

        String token = getToken(request,role);
        String link = "http://localhost:8080/registration/confirm?token=" + token;
        emailSender.send(
                request.getIndirizzoEmail(),
                buildEmail(request.getIndirizzoEmail(), link), "Conferma la tua email");
        return token;
    }

    @Transactional
    public String confirmToken(String token) throws CustomException {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new CustomException(ErrorMessage.TOKEN_NOT_FOUND));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new CustomException(ErrorMessage.EMAIL_ALREADY_CONFIRMED);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorMessage.TOKEN_EXPIRED);
        }

        confirmationTokenService.setConfirmedAt(token);
        userDetailService.enableUser(
                confirmationToken.getUtenteGenerico().getEmail());
        return "redirect:/login?confirmed";
    }

    private String buildEmail(String name, String link) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Conferma il tuo indirizzo e-mail</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Ciao " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Grazie per esserti registrato sul nostro sistema. Clicca il link qui sotto per attivare il tuo account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Attiva il tuo account</a> </p></blockquote>\n Questo link scadrà dopo 15 minuti. <p>A presto!</p>" +
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

    private String getToken(UtenteGenericoDTO request, String role) throws CustomException {

        String token;
        switch(role) {

            case "laboratorioDTO":
                LaboratorioDTO tmp_req = (LaboratorioDTO) request;

                // Creo utente e laboratorio
                UtenteGenerico u1 = new UtenteGenerico(
                        tmp_req.getIndirizzoEmail(), tmp_req.getPassword(), tmp_req.getRuolo());
                Laboratorio l1 = new Laboratorio(tmp_req.getNomeLaboratorio(),
                        tmp_req.getNumeroTelefono(), tmp_req.getIndirizzoStradale(),
                        tmp_req.getCodiceIban(), tmp_req.getPartitaIva(), u1);
                u1.setLaboratorio(l1);

                // Creo calendario e giorni lavorativi
                Calendario calendario = new Calendario();
                if(tmp_req.isLunedi()) {
                    GiornoLavorativo lunedi = new GiornoLavorativo(tmp_req.getOrario_lunedi().getAperturaMattina(),
                            tmp_req.getOrario_lunedi().getChiusuraMattina(),
                            tmp_req.getOrario_lunedi().getAperturaPomeriggio(),
                            tmp_req.getOrario_lunedi().getChiusuraPomeriggio());
                    calendario.setLunedi(lunedi);
                    lunedi.setLunedi(calendario);
                }
                if(tmp_req.isMartedi()) {
                    GiornoLavorativo martedi = new GiornoLavorativo(tmp_req.getOrario_martedi().getAperturaMattina(),
                            tmp_req.getOrario_martedi().getChiusuraMattina(),
                            tmp_req.getOrario_martedi().getAperturaPomeriggio(),
                            tmp_req.getOrario_martedi().getChiusuraPomeriggio());
                    calendario.setMartedi(martedi);
                    martedi.setMartedi(calendario);
                }
                if(tmp_req.isMercoledi()) {
                    GiornoLavorativo mercoledi = new GiornoLavorativo(tmp_req.getOrario_mercoledi().getAperturaMattina(),
                            tmp_req.getOrario_mercoledi().getChiusuraMattina(),
                            tmp_req.getOrario_mercoledi().getAperturaPomeriggio(),
                            tmp_req.getOrario_mercoledi().getChiusuraPomeriggio());
                    calendario.setMercoledi(mercoledi);
                    mercoledi.setMercoledi(calendario);
                }
                if(tmp_req.isGiovedi()) {
                    GiornoLavorativo giovedi = new GiornoLavorativo(tmp_req.getOrario_giovedi().getAperturaMattina(),
                            tmp_req.getOrario_giovedi().getChiusuraMattina(),
                            tmp_req.getOrario_giovedi().getAperturaPomeriggio(),
                            tmp_req.getOrario_giovedi().getChiusuraPomeriggio());
                    calendario.setGiovedi(giovedi);
                    giovedi.setGiovedi(calendario);
                }
                if(tmp_req.isVenerdi()) {
                    GiornoLavorativo venerdi = new GiornoLavorativo(tmp_req.getOrario_venerdi().getAperturaMattina(),
                            tmp_req.getOrario_venerdi().getChiusuraMattina(),
                            tmp_req.getOrario_venerdi().getAperturaPomeriggio(),
                            tmp_req.getOrario_venerdi().getChiusuraPomeriggio());
                    calendario.setVenerdi(venerdi);
                    venerdi.setVenerdi(calendario);
                }
                if(tmp_req.isSabato()) {
                    GiornoLavorativo sabato = new GiornoLavorativo(tmp_req.getOrario_sabato().getAperturaMattina(),
                            tmp_req.getOrario_sabato().getChiusuraMattina(),
                            tmp_req.getOrario_sabato().getAperturaPomeriggio(),
                            tmp_req.getOrario_sabato().getChiusuraPomeriggio());
                    calendario.setSabato(sabato);
                    sabato.setSabato(calendario);
                }
                if(tmp_req.isDomenica()) {
                    GiornoLavorativo domenica = new GiornoLavorativo(tmp_req.getOrario_domenica().getAperturaMattina(),
                            tmp_req.getOrario_domenica().getChiusuraMattina(),
                            tmp_req.getOrario_domenica().getAperturaPomeriggio(),
                            tmp_req.getOrario_domenica().getChiusuraPomeriggio());
                    calendario.setDomenica(domenica);
                    domenica.setDomenica(calendario);
                }

                // Collego calendario e laboratorio
                l1.setCalendario(calendario);
                calendario.setLaboratorio(l1);

                // Creo lista tamponi
                List<LaboratorioTampone> lista = new ArrayList<>();
                if(tmp_req.isMolecolare())
                {
                    Tampone tampone = new Tampone();
                    tampone.setId(Long.valueOf(0));
                    LaboratorioTampone labTamp = new LaboratorioTampone(l1,tampone, tmp_req.getPrezzo_molecolare());
                    lista.add(labTamp);
                }
                if(tmp_req.isAntigenico())
                {
                    Tampone tampone = new Tampone();
                    tampone.setId(Long.valueOf(1));
                    LaboratorioTampone labTamp = new LaboratorioTampone(l1,tampone, tmp_req.getPrezzo_antigenico());
                    lista.add(labTamp);
                }
                if(tmp_req.isSierologico())
                {
                    Tampone tampone = new Tampone();
                    tampone.setId(Long.valueOf(2));
                    LaboratorioTampone labTamp = new LaboratorioTampone(l1,tampone, tmp_req.getPrezzo_sierologico());
                    lista.add(labTamp);
                }

                /* ----- Scrittura e salvataggio file nel DB -----
                // Scrivo su file il calendario
                Long userID = utenteGenericoRepository.count() +1;
                try {
                    File file = new File("src/main/resources/static/calendario/lab_" + userID + ".txt");
                    if(file.createNewFile());
                    else
                        throw new IOException("File not created");
                    FileWriter fw = new FileWriter("src/main/resources/static/calendario/lab_" + userID + ".txt");
                    fw.write(tmp_req.calendarioToString());
                    fw.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }

                // Converto File in MultipartFile
                Path path = Paths.get("src/main/resources/static/calendario/lab_" + userID + ".txt");
                String name = "lab_" + userID + ".txt";
                String originalFileName = "lab_" + userID + ".txt";
                String contentType = "text/plain";
                byte[] content = null;
                try {
                    content = Files.readAllBytes(path);
                } catch(final IOException e) {
                    e.printStackTrace();
                }
                MultipartFile result = new MockMultipartFile(name, originalFileName, contentType, content);

                // Salvo il file nel DB e aggiungo il suo riferimento al laboratorio
                try {
                    FileDB file = storageService.store(result);
                    l1.setCalendario(file);
                } catch(IOException e) {
                    e.printStackTrace();
                }
                --------- */

                // Salvo utente e i relativi tamponi nel DB
                token = userDetailService.signUpUser(u1);
                calendarioLaboratorioRepository.save(calendario);
                laboratorioTamponeRepository.saveAll(lista);
                return token;
        }
        return null; // eliminare alla fine
    }
}