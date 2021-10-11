package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity( name = "medico_generale")
public class MedicoMedicinaGenerale extends UtenteGenerico{

    @Column(
            name = "specializzazione",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String specializzazione;

    @Column(
            name = "indirizzo_stradale",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String indirizzoStudio;

    public MedicoMedicinaGenerale() {
    }

    public MedicoMedicinaGenerale(String email,
                                  String password,
                                  String role,
                                  String specializzazione,
                                  String indirizzoStudio) {
        super(email, password, role);
        this.specializzazione = specializzazione;
        this.indirizzoStudio = indirizzoStudio;
    }
}
