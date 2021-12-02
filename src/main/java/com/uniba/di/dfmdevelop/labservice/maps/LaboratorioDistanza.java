package com.uniba.di.dfmdevelop.labservice.maps;

import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LaboratorioDistanza {
    private Laboratorio laboratorio;
    private Double distanza;
}
