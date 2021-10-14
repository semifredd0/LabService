package com.uniba.di.dfmdevelop.labservice.service.impl;

import com.uniba.di.dfmdevelop.labservice.dao.LaboratorioDao;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.model.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.service.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratorioServiceImpl implements LaboratorioService {

    @Autowired
    private LaboratorioDao dao;

    @Override
    public Laboratorio save(Laboratorio laboratorio) {
        return dao.save(laboratorio);
    }

    @Override
    public Laboratorio getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Laboratorio> getAll() {
        return dao.findAll();
    }

    @Override
    public Laboratorio updateLaboratorio(Laboratorio laboratorio) throws CustomException {
        if(laboratorio != null){
            Laboratorio storedLaboratorio = dao.getById(laboratorio.getId());
            if(storedLaboratorio != null){
                dao.save(laboratorio);
            }
            else{
                throw new CustomException("Il laboratorio specificato non e' stato trovato");
            }
        }
        return laboratorio;
    }

    @Override
    public Laboratorio deleteLaboratorio(Long id) throws CustomException {
        Laboratorio storedLaboratorio = dao.getById(id);
        if(storedLaboratorio != null){
            dao.getById(id);
        }
        else{
            throw new CustomException("Il laboratorio specificato non e' stato trovato");
        }
        return storedLaboratorio;
    }
}
