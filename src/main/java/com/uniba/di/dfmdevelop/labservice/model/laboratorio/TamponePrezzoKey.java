package com.uniba.di.dfmdevelop.labservice.model.laboratorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TamponePrezzoKey implements Serializable {
    @Column(name = "laboratorio_id")
    private Long laboratorioId;
    @Column(name = "tampone_id")
    private Long tamponeId;
}
