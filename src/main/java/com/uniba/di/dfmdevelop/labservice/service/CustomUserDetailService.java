package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.dto.LaboratorioDTO;
import com.uniba.di.dfmdevelop.labservice.dto.UtenteGenericoDTO;
import com.uniba.di.dfmdevelop.labservice.email.EmailSender;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import com.uniba.di.dfmdevelop.labservice.model.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.CalendarioLaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final UtenteGenericoRepository utenteGenericoRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final CalendarioLaboratorioRepository calendarioLaboratorioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public CustomUserDetailService(UtenteGenericoRepository utenteGenericoRepository, LaboratorioRepository laboratorioRepository, LaboratorioRepository laboratorioRepository1, CalendarioLaboratorioRepository calendarioLaboratorioRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
        this.utenteGenericoRepository = utenteGenericoRepository;
        this.laboratorioRepository = laboratorioRepository1;
        this.calendarioLaboratorioRepository = calendarioLaboratorioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
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

        switch(role) {
            case "laboratorioDTO":
                LaboratorioDTO tmp_req = (LaboratorioDTO)request;
                UtenteGenerico utenteGenerico = utenteGenericoRepository.
                        findByEmail(request.getIndirizzoEmail()).get();
                laboratorioRepository.updateNome(utenteGenerico, tmp_req.getNomeLaboratorio());
                laboratorioRepository.updateTelefono(utenteGenerico, tmp_req.getNumeroTelefono());
                laboratorioRepository.updateIndirizzo(utenteGenerico, tmp_req.getIndirizzoStradale());
                laboratorioRepository.updateIban(utenteGenerico, tmp_req.getCodiceIban());
                laboratorioRepository.updatePartitaIva(utenteGenerico, tmp_req.getPartitaIva());
        }
    }

    public void updateCalendar(UtenteGenerico utenteGenerico) {
        // TODO: rimuovere il vecchie istanze di calendario e giorno_lavorativo dal DB
        calendarioLaboratorioRepository.save(utenteGenerico.getLaboratorio().getCalendario());
        laboratorioRepository.updateCalendario(utenteGenerico, utenteGenerico.getLaboratorio().getCalendario());
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