package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Tampone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TamponeRepository extends JpaRepository<Tampone, Long> {

    Optional<Tampone> findById(Long id);
}
