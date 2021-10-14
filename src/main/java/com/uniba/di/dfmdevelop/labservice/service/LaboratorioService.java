package com.uniba.di.dfmdevelop.labservice.service;

import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.model.Laboratorio;

import java.util.List;

public interface LaboratorioService {

    public Laboratorio save(Laboratorio laboratorio);

    public Laboratorio getById(Long id);

    public List<Laboratorio> getAll();

    public Laboratorio updateLaboratorio(Laboratorio laboratorio) throws CustomException;

    public Laboratorio deleteLaboratorio(Long id) throws CustomException;

}
