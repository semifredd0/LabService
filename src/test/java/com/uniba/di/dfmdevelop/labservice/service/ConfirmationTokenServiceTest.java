package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.LabServiceApplication;
import com.uniba.di.dfmdevelop.labservice.model.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabServiceApplication.class)
public class ConfirmationTokenServiceTest {

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Test
    public void saveConfirmationTokenWhenRegisteringOrLoggingIn() {
        log.info("Starting savingToken test");

        log.info("Creazione utente prova per token di prova");
        UtenteGenerico utenteGenerico = new UtenteGenerico();
        utenteGenerico.setId(1L);
        utenteGenerico.setEmail("lab_lecce@labservice.it");
        utenteGenerico.setPassword("prova");
        utenteGenerico.setRole("LABORATORIO");

        log.info("Creazione token di prova");
        ConfirmationToken token = new ConfirmationToken();//EX 2017-01-13T17:09:42.411
        token.setId(1L);
        token.setToken("prova");
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.of(2021,11,5,18,30));
        token.setUtenteGenerico(utenteGenerico);

        confirmationTokenService.saveConfirmationToken(token);
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getToken("prova");

        assertFalse(confirmationToken.isEmpty());
        log.info("Ending savingToken test");
    }

    @Test
    public void setConfirmationDateTimeOfToken(){

        log.info("Creazione utente prova per token di prova");
        UtenteGenerico utenteGenerico = new UtenteGenerico();
        utenteGenerico.setId(1L);
        utenteGenerico.setEmail("lab_lecce@labservice.it");
        utenteGenerico.setPassword("prova");
        utenteGenerico.setRole("LABORATORIO");

        log.info("Creazione token di prova");
        ConfirmationToken token = new ConfirmationToken();
        token.setId(1L);
        token.setToken("prova");
        token.setExpiresAt(LocalDateTime.of(2021,11,5,18,30));
        token.setUtenteGenerico(utenteGenerico);

        int result = confirmationTokenService.setConfirmedAt(token.getToken());


        assertEquals(result, 0);

    }
}
