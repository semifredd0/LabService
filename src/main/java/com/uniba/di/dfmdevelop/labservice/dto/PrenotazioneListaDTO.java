package com.uniba.di.dfmdevelop.labservice.dto;

import com.uniba.di.dfmdevelop.labservice.model.Prenotazione;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneListaDTO {
    private Prenotazione prenotazione;
    private String codiceFiscale;
}
