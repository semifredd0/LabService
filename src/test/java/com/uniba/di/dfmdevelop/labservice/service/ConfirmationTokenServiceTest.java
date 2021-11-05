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
        // Dati per utente di prova
        String email = "labo@prova.com";
        String password = "prova";
        String role = "Laboratorio";

        // Dati per token di prova
        long id = 1;
        String tokenString = "tokenprova";
        LocalDateTime generatedAt = LocalDateTime.now();
        LocalDateTime expiresAt = LocalDateTime.of(2021,11,5,18,30);

        log.info("Creazione utente prova per token di prova");
        UtenteGenerico utenteGenerico = new UtenteGenerico(email,password,role);

        log.info("Creazione token di prova");
        ConfirmationToken token = new ConfirmationToken(tokenString,generatedAt,expiresAt,utenteGenerico);//EX 2017-01-13T17:09:42.411

        Mockito.when(confirmationTokenRepository.getById(token.getId())).thenReturn(token);

        boolean result = confirmationTokenService.saveConfirmationToken(token);

        assertTrue(result);
    }

    @Test
    public void gettingToken(){
        String token = "tokenprova";

        log.info("Getting token");
        Optional<ConfirmationToken> tokenResult = confirmationTokenService.getToken(token);

        if(tokenResult.isEmpty()){
            log.info("No token getted");
            assertFalse(tokenResult.isEmpty());
        }

        log.info("Token getted correctly");
        assertTrue(!tokenResult.isEmpty());
    }
}
