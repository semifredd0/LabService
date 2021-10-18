package com.uniba.di.dfmdevelop.labservice.repository;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.TamponePrezzoKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratorioTamponeRepository extends JpaRepository<LaboratorioTampone, TamponePrezzoKey>  {
}
