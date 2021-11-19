package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.LabServiceApplication;
import com.uniba.di.dfmdevelop.labservice.dto.UtenteGenericoDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabServiceApplication.class)
public class RegistrationServiceTest {

    @Autowired
    RegistrationService registrationService;

    @Test
    public void registration() throws CustomException {   //ritorna una Authentication failed; nested exception is javax.mail.AuthenticationFailedException: failed to connect, no password specified?
        //TODO fixare o eliminare questa classe
        UtenteGenericoDTO utenteGenericoDTO = new UtenteGenericoDTO();
        utenteGenericoDTO.setIndirizzoEmail("lab_bari@labservice.it");
        utenteGenericoDTO.setPassword("prova");
        utenteGenericoDTO.setConferma_password("prova");
        utenteGenericoDTO.setRuolo("LABORATORIO");

        String token = registrationService.register(utenteGenericoDTO,utenteGenericoDTO.getRuolo());

        assertFalse(token.isEmpty());
    }

    @Test
    public void tokenConfirmation() throws CustomException { //Ritorna exception token non trovato

        String token = "prova";

        String confirmToken = registrationService.confirmToken(token);

        assertFalse(confirmToken.isEmpty());
    }
}
