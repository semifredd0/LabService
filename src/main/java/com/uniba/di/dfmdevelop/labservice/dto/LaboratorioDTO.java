package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode
@ToString
public class LaboratorioDTO {

    @NotEmpty(message = "Inserisci l'indirizzo e-mail")
    private String indirizzoEmail;
    @NotEmpty(message = "Inserisci la password")
    private String password;
    @NotEmpty(message = "Inserisci il ruolo")
    private String ruolo;
    @NotEmpty(message = "Inserisci il nome laboratorio")
    private String nomeLaboratorio;
    @NotEmpty(message = "Inserisci il numero di telefono")
    private String numeroTelefono;
    @NotEmpty(message = "Inserisci l'indirizzo stradale")
    private String indirizzoStradale;
    @NotEmpty(message = "Inserisci il codice IBAN")
    private String codiceIban;
    @NotEmpty(message = "Inserisci la partita IVA")
    private String partitaIva;

    public LaboratorioDTO(String indirizzoEmail,
                          String password,
                          String ruolo,
                          String nomeLaboratorio,
                          String numeroTelefono,
                          String indirizzoStradale,
                          String codiceIban,
                          String partitaIva) {

        this.indirizzoEmail = indirizzoEmail;
        this.password = password;
        this.ruolo = ruolo;
        this.nomeLaboratorio = nomeLaboratorio;
        this.numeroTelefono = numeroTelefono;
        this.indirizzoStradale = indirizzoStradale;
        this.codiceIban = codiceIban;
        this.partitaIva = partitaIva;
    }

    public LaboratorioDTO() {}

    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    public String getPassword() {
        return password;
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
}
