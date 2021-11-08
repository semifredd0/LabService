package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.model.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.ConfirmationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenService confirmationTokenService;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this.confirmationTokenService);
    }

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

        boolean result = confirmationTokenService.saveConfirmationToken(token);

        assertTrue(result);
        log.info("Ending savingToken test");
    }

    @Test
    public void gettingToken(){
        log.info("Starting gettingToken test");

        String token = "tokenprova";

        ConfirmationToken foundToken = new ConfirmationToken();
        foundToken.setId(200L);
        foundToken.setToken(token);

        log.info("Getting token");
        Optional<ConfirmationToken> tokenResult = confirmationTokenService.getToken(token);

        assertTrue(tokenResult.isEmpty());
        log.info("Ending gettingToken test");
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


        assertFalse(result == 1);

    }
}
