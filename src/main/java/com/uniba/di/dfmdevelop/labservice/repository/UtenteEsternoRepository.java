package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.DatoreLavoro;
import com.uniba.di.dfmdevelop.labservice.model.UtenteEsterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtenteEsternoRepository extends JpaRepository<UtenteEsterno, Long> {

    List<UtenteEsterno> findUtenteEsternoByDatoreLavoro(@Param("datore_lavoro") DatoreLavoro datoreLavoro);
    UtenteEsterno getUtenteEsternoById(@Param("id_dipendente") Long idDipendente);
}
