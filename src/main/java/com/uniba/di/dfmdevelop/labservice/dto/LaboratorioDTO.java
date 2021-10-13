package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class LaboratorioDTO {

    @NotEmpty
    @Email(message = "Il campo deve essere del formato corretto")
    private String indirizzoEmail;

    @NotEmpty
    @Size(min = 6, message = "Il campo deve contenere almeno 6 caratteri")
    private String password;

    @NotEmpty
    private String conferma_password;

    // TODO: enum
    @NotEmpty
    private String ruolo;

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
                          String ruolo,
                          String nomeLaboratorio,
                          String numeroTelefono,
                          String indirizzoStradale,
                          String codiceIban,
                          String partitaIva) {
        super();
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

    public void setIndirizzoEmail(String indirizzoEmail) {
        this.indirizzoEmail = indirizzoEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConferma_password(String conferma_password) {
        this.conferma_password = conferma_password;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public void setNomeLaboratorio(String nomeLaboratorio) {
        this.nomeLaboratorio = nomeLaboratorio;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setIndirizzoStradale(String indirizzoStradale) {
        this.indirizzoStradale = indirizzoStradale;
    }

    public void setCodiceIban(String codiceIban) {
        this.codiceIban = codiceIban;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
}
