package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.model.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.ConfirmationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConfirmationTokenServiceTest {

    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenService confirmationTokenService;

    @Test
    public void saveConfirmationTokenWhenRegisteringOrLoggingIn() {
        log.info("Starting savingToken test");
        // Dati per utente di prova
        String email = "labo@prova.com";
        String password = "prova";
        String role = "Laboratorio";

        // Dati per token di prova
        String tokenString = "tokenprova";
        LocalDateTime generatedAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.of(2021,11,5,18,30);

        log.info("Creazione utente prova per token di prova");
        UtenteGenerico utenteGenerico = new UtenteGenerico(email,password,role);

        log.info("Creazione token di prova");
        ConfirmationToken token = new ConfirmationToken(tokenString,generatedAt,expiresAt,utenteGenerico);//EX 2017-01-13T17:09:42.411

        Mockito.when(confirmationTokenRepository.getById(token.getId())).thenReturn(token);

        boolean result = confirmationTokenService.saveConfirmationToken(token);

        log.info(token.toString());

        assertFalse(result);
        log.info("Ending savingToken test");
    }

    @Test
    public void gettingToken(){
        log.info("Starting gettingToken test");

        String token = "tokenprova";

        ConfirmationToken foundToken = new ConfirmationToken();
        foundToken.setId(200L);
        foundToken.setToken(token);

        Mockito.when(confirmationTokenRepository.findByToken(token)).thenReturn(Optional.of(foundToken));

        log.info("Getting token");
        Optional<ConfirmationToken> tokenResult = confirmationTokenService.getToken(token);

        assertTrue("token not found", tokenResult.isPresent());
        log.info("Ending gettingToken test");
    }
}
