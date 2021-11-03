package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarioLaboratorioRepository extends JpaRepository<Calendario, Long> {

    Optional<Calendario> findById(Long id);
}
