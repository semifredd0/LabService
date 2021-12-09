package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
public class PrenotazioneCittadinoDTO {

    @NotEmpty(message = "Compilare questo campo")
    @Email(message = "Il campo deve essere del formato corretto")
    protected String indirizzoEmail;

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String nome;

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String cognome;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascita;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Il campo deve contenere esattamente 10 caratteri")
    private String numeroTelefono;

    @NotEmpty
    @Size(min = 16, max = 16, message = "Il campo deve contenere esattamente 16 caratteri")
    private String codiceFiscale;

    private double prezzo;
    private Long idLaboratorio;
    private Long idTampone;
    private LocalDate dataPrenotazione;
    private boolean pagamento; // 0-Sede 1-Paypal

    public PrenotazioneCittadinoDTO() {}
}
