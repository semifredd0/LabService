package com.uniba.di.dfmdevelop.labservice.registration.requests;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class LaboratorioRegistrationRequest extends RegistrationRequest {

    private final String nomeLaboratorio;
    private final String numeroTelefono;
    private final String indirizzoStradale;
    private final String codiceIban;
    private final String partitaIva;

    public LaboratorioRegistrationRequest(String indirizzoEmail,
                                          String password,
                                          String ruolo,
                                          String nomeLaboratorio,
                                          String numeroTelefono,
                                          String indirizzoStradale,
                                          String codiceIban,
                                          String partitaIva) {

        super(indirizzoEmail, password, ruolo);
        this.nomeLaboratorio = nomeLaboratorio;
        this.numeroTelefono = numeroTelefono;
        this.indirizzoStradale = indirizzoStradale;
        this.codiceIban = codiceIban;
        this.partitaIva = partitaIva;
    }
}
