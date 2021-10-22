package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

    @Query("SELECT l FROM laboratorio l WHERE l.utenteGenerico = ?1")
    Laboratorio getByIdUtente(UtenteGenerico utenteGenerico);
}
