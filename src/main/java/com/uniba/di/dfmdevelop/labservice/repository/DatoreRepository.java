package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.DatoreLavoro;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface DatoreRepository extends JpaRepository<DatoreLavoro, Long> {

    @Query("SELECT c FROM datore_lavoro c WHERE c.utenteGenerico = ?1")
    DatoreLavoro getByIdUtente(UtenteGenerico utenteGenerico);

    @Transactional
    @Modifying
    @Query("UPDATE datore_lavoro c SET c.nome = ?2 WHERE c.utenteGenerico = ?1")
    int updateNome(UtenteGenerico utenteGenerico, String nome);

    @Transactional
    @Modifying
    @Query("UPDATE datore_lavoro c SET c.cognome = ?2 WHERE c.utenteGenerico = ?1")
    int updateCognome(UtenteGenerico utenteGenerico, String cognome);

    @Transactional
    @Modifying
    @Query("UPDATE datore_lavoro c SET c.dataNascita = ?2 WHERE c.utenteGenerico = ?1")
    int updateDataNascita(UtenteGenerico utenteGenerico, LocalDate dataNascita);

    @Transactional
    @Modifying
    @Query("UPDATE datore_lavoro c SET c.nomeAzienda = ?2 WHERE c.utenteGenerico = ?1")
    int updateNomeAzienda(UtenteGenerico utenteGenerico, String nomeAzienda);

    @Transactional
    @Modifying
    @Query("UPDATE datore_lavoro c SET c.indirizzoAzienda = ?2 WHERE c.utenteGenerico = ?1")
    int updateIndirizzoAzienda(UtenteGenerico utenteGenerico, String indirizzoAzienda);

    @Transactional
    @Modifying
    @Query("UPDATE datore_lavoro c SET c.numeroTelefono = ?2 WHERE c.utenteGenerico = ?1")
    int updateNumeroTelefono(UtenteGenerico utenteGenerico, String numeroTelefono);
}
