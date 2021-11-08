package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class CustomUserDetailServiceJpaTest {

    @Autowired
    UtenteGenericoRepository utenteGenericoRepository;

    @Test
    @Order(1)
    public void loadUserByUsername(){

        Optional<UtenteGenerico> userDetails = utenteGenericoRepository.findByEmail("lab_bari@labservice.it");

        assertFalse(userDetails.isEmpty());

    }

}
