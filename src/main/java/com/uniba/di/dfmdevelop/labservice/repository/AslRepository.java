package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.Asl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AslRepository extends JpaRepository<Asl, Long> {

    Optional<Asl> findById(Long id);

    Asl save(final Asl asl);
}
