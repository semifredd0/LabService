package com.uniba.di.dfmdevelop.labservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniba.di.dfmdevelop.labservice.LabServiceApplication;
import com.uniba.di.dfmdevelop.labservice.dto.UtenteGenericoDTO;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Calendario;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.GiornoLavorativo;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabServiceApplication.class)
public class CustomUserDetailServiceTest {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Test
    public void loadUserByUsername() throws JsonProcessingException {

       ObjectMapper mapper = new ObjectMapper();

       UserDetails userDetails = customUserDetailService.loadUserByUsername("lab_lecce@labservice.it");

        assertTrue(userDetails != null);
    }

    @Test
    public void signingUpUser() throws CustomException {
        log.info("Starting sign up test");

        UtenteGenerico utenteGenerico = new UtenteGenerico();
        utenteGenerico.setId(1L);
        utenteGenerico.setEmail("lab_bisceglie@labservice.it");
        utenteGenerico.setEnabled(true);
        utenteGenerico.setPassword("laboprova");
        utenteGenerico.setRole("LABORATORIO");
        utenteGenerico.setResetPasswordToken(null);

        log.info("Signing up for utente: " + utenteGenerico.getEmail());
        String token = customUserDetailService.signUpUser(utenteGenerico);

        assertFalse(token.isEmpty());
    }

    @Test
    public void enablingUser() {
        log.info("Starting enabling user test");

        int result = customUserDetailService.enableUser("lab_bari@labservice.it");

       UserDetails utenteGenerico = customUserDetailService.loadUserByUsername("lab_bari@labservice.it");

        assertTrue(utenteGenerico.isEnabled());
    }

    @Test
    public void updatingUserDatas (){

       boolean result = false;

        UtenteGenericoDTO utenteGenericoDTO = new UtenteGenericoDTO();
        utenteGenericoDTO.setIndirizzoEmail("lab_bari@labservice.it");
        utenteGenericoDTO.setPassword("prova");
        utenteGenericoDTO.setConferma_password("prova");
        utenteGenericoDTO.setRuolo("LABORATORIO");

        log.info("Updating datas");
        try {
            customUserDetailService.updateUser(utenteGenericoDTO, utenteGenericoDTO.getRuolo());
            result = true;
            log.info("Test Completed!!");
        }catch (Exception e){
            log.info("Test Failed");
        }

        assertTrue(result);
    }

    @Test
    public void updatingCalendar(){

       boolean result = false;

        GiornoLavorativo giornoLavorativo = new GiornoLavorativo();
        giornoLavorativo.setAperturaMattina("10");
        giornoLavorativo.setChiusuraMattina("12");
        giornoLavorativo.setAperturaPomeriggio("15");
        giornoLavorativo.setChiusuraPomeriggio("18");

        Calendario calendario = new Calendario();
        calendario.setLunedi(giornoLavorativo);
        calendario.setMartedi(giornoLavorativo);
        calendario.setMercoledi(giornoLavorativo);
        calendario.setGiovedi(giornoLavorativo);
        calendario.setVenerdi(giornoLavorativo);
        calendario.setSabato(giornoLavorativo);
        calendario.setDomenica(giornoLavorativo);

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Lab Lecce");
        laboratorio.setCalendario(calendario);

        UtenteGenerico utenteGenerico = new UtenteGenerico();
        utenteGenerico.setId(1L);
        utenteGenerico.setEmail("lab_lecce@labservice.it");
        utenteGenerico.setEnabled(true);
        utenteGenerico.setPassword("$2a$10$JyJfPYqsgo1byjOvOS0uq.DJ.qDaiazDz4UnTZV3LvZTllx7Ro..a");
        utenteGenerico.setRole("LABORATORIO");
        utenteGenerico.setLaboratorio(laboratorio);

       try{
           customUserDetailService.updateCalendar(utenteGenerico);
           result = true;
           log.info("Test Completed!!");
       }catch(Exception e){
           log.info("Test failed");
       }

       assertTrue(result);
    }

    @Test
    public void updatingTokenForResetPassword() {

        boolean result = false;
        String newToken = "prova";
        String email = "lab_bari@labservice.it";

        try{
           customUserDetailService.updateResetPasswordToken(newToken,email);
           result = true;
           log.info("Test Completed!!");
        }catch(Exception e){
           log.info("Test Failed");
        }

        assertTrue(result);
    }

    @Test
    public void getUserByTokenForReset() {

        String newToken = "prova";
        String email = "lab_bari@labservice.it";

        UtenteGenerico result =null;
        try{
            customUserDetailService.updateResetPasswordToken(newToken,email);
            result = customUserDetailService.getByResetPasswordToken(newToken);
            log.info("Test Completed!!");
        }catch(Exception e){
            log.info("Test Failed");
        }

        assertTrue(result != null);
    }

    @Test
    public void updatingPassword() {

        String oldPassword = "$2a$10$JyJfPYqsgo1byjOvOS0uq.DJ.qDaiazDz4UnTZV3LvZTllx7Ro..a";
        String newPassword = "prova";

        UtenteGenerico utenteGenerico = new UtenteGenerico();
        utenteGenerico.setId(1L);
        utenteGenerico.setEmail("lab_lecce@labservice.it");
        utenteGenerico.setEnabled(true);
        utenteGenerico.setPassword(oldPassword);
        utenteGenerico.setRole("LABORATORIO");

        customUserDetailService.updatePassword(utenteGenerico,newPassword);

        assertTrue(oldPassword != utenteGenerico.getPassword());
    }
}
