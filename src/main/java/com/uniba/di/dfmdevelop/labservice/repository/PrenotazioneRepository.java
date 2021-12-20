package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.Prenotazione;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    List<Prenotazione> findPrenotazioneByLaboratorioTampone_Laboratorio(@Param("laboratorio") Laboratorio laboratorio);
}
