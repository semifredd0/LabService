package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.FileDB;
import com.uniba.di.dfmdevelop.labservice.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

    @Transactional
    List<FileDB> findFileDBByPrenotazione(Prenotazione prenotazione);
}