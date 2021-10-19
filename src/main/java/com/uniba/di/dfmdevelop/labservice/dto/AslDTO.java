package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@ToString
public class AslDTO extends UtenteGenericoDTO{

    @NotEmpty
    @Size(min = 3, message = "Il campo deve contenere almeno 3 caratteri")
    private String zonaRiferimento;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Il campo deve contenere almeno 3 caratteri")
    private String telefono;

    @NotEmpty
    @Size(min = 3, message = "Il campo deve contenere almeno 3 caratteri")
    private String indirizzo;

    @NotEmpty
    @Size(min = 11, max = 11, message = "Il campo deve contenere almeno 3 caratteri")
    private String partitaIva;

    public AslDTO(String indirizzoEmail,
                  String password,
                  String confermaPassword,
                  String ruolo,
                  String zonaRiferimento,
                  String telefono,
                  String indirizzo,
                  String partitaIva) {
        super(indirizzoEmail,password,ruolo,confermaPassword);
        this.zonaRiferimento = zonaRiferimento;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.partitaIva = partitaIva;
    }

    public AslDTO() { super(); }

    public String getZonaRiferimento() {
        return zonaRiferimento;
    }

    public void setZonaRiferimento(String zonaRiferimento) {
        this.zonaRiferimento = zonaRiferimento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }
}
