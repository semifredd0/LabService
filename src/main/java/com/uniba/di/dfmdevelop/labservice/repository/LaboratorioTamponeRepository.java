package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Tampone;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.TamponePrezzoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LaboratorioTamponeRepository extends JpaRepository<LaboratorioTampone, TamponePrezzoKey>  {

    @Query("SELECT l FROM laboratorio_tampone l WHERE l.laboratorio = ?1")
    List<LaboratorioTampone> getByLaboratorio(Laboratorio laboratorio);

    @Query("SELECT l FROM laboratorio_tampone l WHERE l.laboratorio = ?1 AND l.tampone = ?2")
    LaboratorioTampone getItem(Laboratorio laboratoriomì, Tampone tampone);
}
