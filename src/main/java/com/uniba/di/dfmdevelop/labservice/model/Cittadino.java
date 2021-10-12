/*
package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "cittadino")
public class Cittadino extends UtenteGenerico {

    @Column(
            name = "data_nascita",
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date dataNascita;

    @Column(
            name = "codice_fiscale",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String codFiscale;

    public Cittadino() {
    }

    public Cittadino(String email,
                     String password,
                     String role,
                     Date dataNascita,
                     String codFiscale) {
        super(email, password, role);
        this.dataNascita = dataNascita;
        this.codFiscale = codFiscale;
    }
}
*/