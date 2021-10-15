package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class LaboratorioDTO extends UtenteGenericoDTO {

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String nomeLaboratorio;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Il campo deve contenere esattamente 10 caratteri")
    private String numeroTelefono;

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String indirizzoStradale;

    @NotEmpty
    @Size(min = 27, max = 27, message = "Il campo deve contenere esattamente 27 caratteri")
    private String codiceIban;

    @NotEmpty
    @Size(min = 11, max = 11, message = "Il campo deve contenere esattamente 11 caratteri")
    private String partitaIva;

    public LaboratorioDTO(String indirizzoEmail,
                          String password,
                          String confermaPassword,
                          String ruolo,
                          String nomeLaboratorio,
                          String numeroTelefono,
                          String indirizzoStradale,
                          String codiceIban,
                          String partitaIva) {
        super(indirizzoEmail,password,ruolo,confermaPassword);
        this.nomeLaboratorio = nomeLaboratorio;
        this.numeroTelefono = numeroTelefono;
        this.indirizzoStradale = indirizzoStradale;
        this.codiceIban = codiceIban;
        this.partitaIva = partitaIva;
    }

    public LaboratorioDTO() {
        super();
    }

    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getConferma_password() {
        return conferma_password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public String getNomeLaboratorio() {
        return nomeLaboratorio;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getIndirizzoStradale() {
        return indirizzoStradale;
    }

    public String getCodiceIban() {
        return codiceIban;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
