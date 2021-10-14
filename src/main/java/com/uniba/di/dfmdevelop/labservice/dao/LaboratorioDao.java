package com.uniba.di.dfmdevelop.labservice.dao;

import com.uniba.di.dfmdevelop.labservice.model.Laboratorio;

public interface LaboratorioDao extends BaseDao<Laboratorio> {

    //@Query("update Laboratorio l set l.indirizzo_email = :email, l.password = :password,l.numero_telefono = :telefono, l.indirizzo_stradale = :indirizzo, l.codice_iban = :iban")
    //Laboratorio updateLaboratorio(Laboratorio laboratorio);

}
