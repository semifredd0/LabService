package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
public class PrenotazioneDipendenteDTO {

    private double prezzo;
    private Long idDipendente;
    private Long idLaboratorio;
    private Long idTampone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPrenotazione;

    private boolean pagamento; // 0-Sede 1-Paypal

    public PrenotazioneDipendenteDTO() {}
}
