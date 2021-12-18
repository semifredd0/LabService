package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.cittadino.Cittadino;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface CittadinoRepository extends JpaRepository<Cittadino, Long> {

    @Query("SELECT c FROM cittadino c WHERE c.utenteGenerico = ?1")
    Cittadino getByIdUtente(UtenteGenerico utenteGenerico);

    @Transactional
    @Modifying
    @Query("UPDATE cittadino c SET c.nome = ?2 WHERE c.utenteGenerico = ?1")
    int updateNome(UtenteGenerico utenteGenerico, String nome);

    @Transactional
    @Modifying
    @Query("UPDATE cittadino c SET c.cognome = ?2 WHERE c.utenteGenerico = ?1")
    int updateCognome(UtenteGenerico utenteGenerico, String cognome);

    @Transactional
    @Modifying
    @Query("UPDATE cittadino c SET c.dataNascita = ?2 WHERE c.utenteGenerico = ?1")
    int updateDataNascita(UtenteGenerico utenteGenerico, LocalDate dataNascita);

    @Transactional
    @Modifying
    @Query("UPDATE cittadino c SET c.numeroTelefono = ?2 WHERE c.utenteGenerico = ?1")
    int updateNumeroTelefono(UtenteGenerico utenteGenerico, String numeroTelefono);

    @Transactional
    @Modifying
    @Query("UPDATE cittadino c SET c.codFiscale = ?2 WHERE c.utenteGenerico = ?1")
    int updateCodiceFiscale(UtenteGenerico utenteGenerico, String codiceFiscale);
}
