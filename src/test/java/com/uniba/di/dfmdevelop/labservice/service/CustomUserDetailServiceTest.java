package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.email.EmailSender;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.CalendarioLaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomUserDetailServiceTest {

    @Mock
    UtenteGenericoRepository utenteGenericoRepository;

    @Mock
    LaboratorioRepository laboratorioRepository;

    @Mock
    CalendarioLaboratorioRepository calendarioLaboratorioRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @Mock
    EmailSender emailSender;

    @InjectMocks
    CustomUserDetailService customUserDetailService;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this.customUserDetailService);
    }

    @Test
    public void signingUpUser() throws CustomException {
        log.info("Starting sign up test");

        UtenteGenerico utenteGenerico = new UtenteGenerico();
        utenteGenerico.setId(5L);
        utenteGenerico.setEmail("labprova@labservice.it");
        utenteGenerico.setEnabled(true);
        utenteGenerico.setPassword("giovanni");
        utenteGenerico.setRole("LABORATORIO");
        utenteGenerico.setResetPasswordToken("sdfsdfsdfsd");

        log.info("Signing up for utente: " + utenteGenerico.getEmail());
        String token = customUserDetailService.signUpUser(utenteGenerico);

        assertFalse(token.isEmpty());
    }
}
