package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.MedicoMedicinaGenerale;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface MedicoRepository extends JpaRepository<MedicoMedicinaGenerale, Long> {

    @Query("SELECT c FROM medico_medicina_generale c WHERE c.utenteGenerico = ?1")
    MedicoMedicinaGenerale getByIdUtente(UtenteGenerico utenteGenerico);

    @Transactional
    @Modifying
    @Query("UPDATE medico_medicina_generale c SET c.nome = ?2 WHERE c.utenteGenerico = ?1")
    int updateNome(UtenteGenerico utenteGenerico, String nome);

    @Transactional
    @Modifying
    @Query("UPDATE medico_medicina_generale c SET c.cognome = ?2 WHERE c.utenteGenerico = ?1")
    int updateCognome(UtenteGenerico utenteGenerico, String cognome);

    @Transactional
    @Modifying
    @Query("UPDATE medico_medicina_generale c SET c.dataNascita = ?2 WHERE c.utenteGenerico = ?1")
    int updateDataNascita(UtenteGenerico utenteGenerico, LocalDate dataNascita);

    @Transactional
    @Modifying
    @Query("UPDATE medico_medicina_generale c SET c.specializzazione = ?2 WHERE c.utenteGenerico = ?1")
    int updateSpecializzazione(UtenteGenerico utenteGenerico, String specializzazione);

    @Transactional
    @Modifying
    @Query("UPDATE medico_medicina_generale c SET c.indirizzoStudio = ?2 WHERE c.utenteGenerico = ?1")
    int updateIndirizzoStudio(UtenteGenerico utenteGenerico, String indirizzoStudio);

    @Transactional
    @Modifying
    @Query("UPDATE medico_medicina_generale c SET c.numeroTelefono = ?2 WHERE c.utenteGenerico = ?1")
    int updateNumeroTelefono(UtenteGenerico utenteGenerico, String numeroTelefono);
}
