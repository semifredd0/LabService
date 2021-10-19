package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    Optional<Laboratorio> findById(Long id);

    Laboratorio save(final Laboratorio laboratorio);

    @Transactional
    @Modifying
    @Query("UPDATE laboratorio l SET l.IBAN = ?2, l.indirizzo = ?3, l.nome = ?4, l.partitaIVA =?5, l.telefono = ?6 WHERE l.id = ?1")
    void modifyLaborartorio(Laboratorio laboratorio);

    Calendar save(final Calendar calendario);
}
