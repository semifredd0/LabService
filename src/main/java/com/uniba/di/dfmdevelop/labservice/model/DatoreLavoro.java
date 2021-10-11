package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity( name = "datore_lavoro")
public class DatoreLavoro extends UtenteGenerico{

    @Column(
            name = "nome_azienda",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String nomeAzienda;

    @Column(
            name = "indirizzo_stradale",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String indirizzoAzienda;

    public DatoreLavoro() {
    }

    public DatoreLavoro(String email,
                        String password,
                        String role,
                        String nomeAzienda,
                        String indirizzoAzienda) {
        super(email, password, role);
        this.nomeAzienda = nomeAzienda;
        this.indirizzoAzienda = indirizzoAzienda;
    }
}
