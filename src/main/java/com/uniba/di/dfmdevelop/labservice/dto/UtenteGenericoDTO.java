package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class UtenteGenericoDTO {

    @NotEmpty
    @Email(message = "Il campo deve essere del formato corretto")
    protected String indirizzoEmail;

    @NotEmpty
    @Size(min = 6, message = "Il campo deve contenere almeno 6 caratteri")
    protected String password;

    @NotEmpty
    protected String conferma_password;

    @NotEmpty
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

    public void setPassword(String password) {
        this.password = password;
    }
}
