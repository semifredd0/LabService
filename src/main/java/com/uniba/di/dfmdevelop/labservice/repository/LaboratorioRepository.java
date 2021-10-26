package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    @Query("SELECT l FROM laboratorio l WHERE l.utenteGenerico = ?1")
    Laboratorio getByIdUtente(UtenteGenerico utenteGenerico);

    @Transactional
    @Modifying
    @Query("UPDATE laboratorio l SET l.nome = ?2 WHERE l.utenteGenerico = ?1")
    int updateNome(UtenteGenerico utenteGenerico, String nome);

    @Transactional
    @Modifying
    @Query("UPDATE laboratorio l SET l.telefono = ?2 WHERE l.utenteGenerico = ?1")
    int updateTelefono(UtenteGenerico utenteGenerico, String telefono);

    @Transactional
    @Modifying
    @Query("UPDATE laboratorio l SET l.indirizzo = ?2 WHERE l.utenteGenerico = ?1")
    int updateIndirizzo(UtenteGenerico utenteGenerico, String indirizzo);

    @Transactional
    @Modifying
    @Query("UPDATE laboratorio l SET l.IBAN = ?2 WHERE l.utenteGenerico = ?1")
    int updateIban(UtenteGenerico utenteGenerico, String IBAN);

    @Transactional
    @Modifying
    @Query("UPDATE laboratorio l SET l.partitaIVA = ?2 WHERE l.utenteGenerico = ?1")
    int updatePartitaIva(UtenteGenerico utenteGenerico, String partitaIVA);
}
