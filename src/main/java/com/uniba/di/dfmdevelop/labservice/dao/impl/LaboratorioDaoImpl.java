package com.uniba.di.dfmdevelop.labservice.dao.impl;

import com.uniba.di.dfmdevelop.labservice.dao.LaboratorioDao;
import com.uniba.di.dfmdevelop.labservice.model.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LaboratorioDaoImpl implements LaboratorioDao {

    @Autowired
    LaboratorioRepository laboratorioRepository;

    @Override
    public Laboratorio save(Laboratorio domainObject) {
        return laboratorioRepository.save(domainObject);
    }

    @Override
    public List<Laboratorio> findAll() {
        return laboratorioRepository.findAll();
    }

    @Override
    public Laboratorio getById(Long id) {
        Optional<Laboratorio> object = laboratorioRepository.findById(id);
        return object.orElse(null);
    }

    @Override
    public void delete(Laboratorio domainObject) {
        laboratorioRepository.delete(domainObject);
    }

    @Override
    public void delete(Long id) {
        laboratorioRepository.deleteById(id);
    }

    /*@Override
    public Laboratorio updateLaboratorio(Laboratorio laboratorio) {
        return null;
    }*/


}
