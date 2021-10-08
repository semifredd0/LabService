package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UtenteGenericoRepository extends JpaRepository<UtenteGenerico, Long> {

    Optional<UtenteGenerico> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE utente_generico a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUtenteGenerico(String email);
}