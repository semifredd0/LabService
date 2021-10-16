package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class UtenteGenericoDTO {

    @NotEmpty(message = "Compilare questo campo")
    @Email(message = "Il campo deve essere del formato corretto")
    protected String indirizzoEmail;

    @NotEmpty(message = "Compilare questo campo")
    @Size(min = 6, message = "Il campo deve contenere almeno 6 caratteri")
    protected String password;

    @NotEmpty(message = "Compilare questo campo")
    protected String conferma_password;

    protected String ruolo;

    public UtenteGenericoDTO(String indirizzoEmail, String password, String conferma_password, String ruolo) {
        super();
        this.indirizzoEmail = indirizzoEmail;
        this.password = password;
        this.conferma_password = conferma_password;
        this.ruolo = ruolo;
    }

    public UtenteGenericoDTO() {}

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
}
