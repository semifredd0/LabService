package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.dto.*;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.model.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import com.uniba.di.dfmdevelop.labservice.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final UtenteGenericoRepository utenteGenericoRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final CalendarioLaboratorioRepository calendarioLaboratorioRepository;
    private final LaboratorioTamponeRepository laboratorioTamponeRepository;
    private final MedicoRepository medicoRepository;
    private final DatoreRepository datoreRepository;
    private final CittadinoRepository cittadinoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public CustomUserDetailService(UtenteGenericoRepository utenteGenericoRepository,
                                   LaboratorioRepository laboratorioRepository,
                                   CalendarioLaboratorioRepository calendarioLaboratorioRepository,
                                   LaboratorioTamponeRepository laboratorioTamponeRepository,
                                   MedicoRepository medicoRepository,
                                   DatoreRepository datoreRepository,
                                   BCryptPasswordEncoder bCryptPasswordEncoder,
                                   ConfirmationTokenService confirmationTokenService,
                                   CittadinoRepository cittadinoRepository) {
        this.utenteGenericoRepository = utenteGenericoRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.calendarioLaboratorioRepository = calendarioLaboratorioRepository;
        this.laboratorioTamponeRepository = laboratorioTamponeRepository;
        this.medicoRepository = medicoRepository;
        this.datoreRepository = datoreRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.cittadinoRepository = cittadinoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utenteGenericoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(UtenteGenerico utenteGenerico) throws CustomException {
        boolean userExists = utenteGenericoRepository
                .findByEmail(utenteGenerico.getEmail()).isPresent();

        if (userExists) {
            throw new CustomException(ErrorMessage.EMAIL_ALREADY_TAKEN);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(utenteGenerico.getPassword());
        utenteGenerico.setPassword(encodedPassword);
        utenteGenericoRepository.save(utenteGenerico);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                utenteGenerico
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableUser(String email) {
        return utenteGenericoRepository.enableUtenteGenerico(email);
    }

    public void updateUser(UtenteGenericoDTO request, String role) {
            switch (role) {
                case "laboratorioDTO":
                    LaboratorioDTO tmp_req1 = (LaboratorioDTO) request;
                    UtenteGenerico utenteGenerico1 = utenteGenericoRepository.
                            findByEmail(request.getIndirizzoEmail()).get();
                    laboratorioRepository.updateNome(utenteGenerico1, tmp_req1.getNomeLaboratorio());
                    laboratorioRepository.updateTelefono(utenteGenerico1, tmp_req1.getNumeroTelefono());
                    laboratorioRepository.updateIndirizzo(utenteGenerico1, tmp_req1.getIndirizzoStradale());
                    laboratorioRepository.updateIban(utenteGenerico1, tmp_req1.getCodiceIban());
                    laboratorioRepository.updatePartitaIva(utenteGenerico1, tmp_req1.getPartitaIva());
                    break;
                case "cittadinoDTO":
                    CittadinoDTO tmp_req2 = (CittadinoDTO) request;
                    UtenteGenerico utenteGenerico2 = utenteGenericoRepository.
                            findByEmail(request.getIndirizzoEmail()).get();
                    cittadinoRepository.updateNome(utenteGenerico2, tmp_req2.getNome());
                    cittadinoRepository.updateCognome(utenteGenerico2, tmp_req2.getCognome());
                    cittadinoRepository.updateDataNascita(utenteGenerico2, tmp_req2.getDataNascita());
                    cittadinoRepository.updateNumeroTelefono(utenteGenerico2, tmp_req2.getNumeroTelefono());
                    cittadinoRepository.updateCodiceFiscale(utenteGenerico2, tmp_req2.getCodiceFiscale());
                    break;
                case "medicoDTO":
                    MedicoDTO tmp_req3 = (MedicoDTO) request;
                    UtenteGenerico utenteGenerico3 = utenteGenericoRepository.
                            findByEmail(request.getIndirizzoEmail()).get();
                    medicoRepository.updateNome(utenteGenerico3, tmp_req3.getNome());
                    medicoRepository.updateCognome(utenteGenerico3, tmp_req3.getCognome());
                    medicoRepository.updateDataNascita(utenteGenerico3, tmp_req3.getDataNascita());
                    medicoRepository.updateSpecializzazione(utenteGenerico3, tmp_req3.getSpecializzazione());
                    medicoRepository.updateIndirizzoStudio(utenteGenerico3, tmp_req3.getIndirizzoStudio());
                    medicoRepository.updateNumeroTelefono(utenteGenerico3, tmp_req3.getNumeroTelefono());
                    break;
                case "datoreDTO":
                    DatoreDTO tmp_req4 = (DatoreDTO) request;
                    UtenteGenerico utenteGenerico4 = utenteGenericoRepository.
                            findByEmail(request.getIndirizzoEmail()).get();
                    datoreRepository.updateNome(utenteGenerico4, tmp_req4.getNome());
                    datoreRepository.updateCognome(utenteGenerico4, tmp_req4.getCognome());
                    datoreRepository.updateDataNascita(utenteGenerico4, tmp_req4.getDataNascita());
                    datoreRepository.updateNomeAzienda(utenteGenerico4, tmp_req4.getNomeAzienda());
                    datoreRepository.updateIndirizzoAzienda(utenteGenerico4, tmp_req4.getIndirizzoAzienda());
                    datoreRepository.updateNumeroTelefono(utenteGenerico4, tmp_req4.getNumeroTelefono());
                    break;
            }
    }

    public void updateCalendar(UtenteGenerico utenteGenerico) {
        // TODO: rimuovere il vecchie istanze di calendario e giorno_lavorativo dal DB
        calendarioLaboratorioRepository.save(utenteGenerico.getLaboratorio().getCalendario());
        laboratorioRepository.updateCalendario(utenteGenerico, utenteGenerico.getLaboratorio().getCalendario());
    }

    public List<Laboratorio> getAllLaboratorio() {
        return laboratorioRepository.getAllLaboratorio();
    }

    public List<LaboratorioTampone> getListTampone(Laboratorio laboratorio) {
        return laboratorioTamponeRepository.getByLaboratorio(laboratorio);
    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        UtenteGenerico utenteGenerico = utenteGenericoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        utenteGenerico.setResetPasswordToken(token);
        utenteGenericoRepository.save(utenteGenerico);
    }

    public UtenteGenerico getByResetPasswordToken(String token) {
        return utenteGenericoRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(UtenteGenerico utenteGenerico, String newPassword) {
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        utenteGenerico.setPassword(encodedPassword);
        utenteGenerico.setResetPasswordToken(null);
        utenteGenericoRepository.save(utenteGenerico);
    }
}