package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
@ToString
public class CittadinoDTO extends UtenteGenericoDTO {

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String nome;

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String cognome;

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere una data valida")
    private String dataNascita;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Il campo deve contenere esattamente 10 caratteri")
    private String numeroTelefono;

    @NotEmpty
    @Size(min = 16, max = 16, message = "Il campo deve contenere esattamente 16 caratteri")
    private String codiceFiscale;

    public CittadinoDTO() {
        super();
    }
}
