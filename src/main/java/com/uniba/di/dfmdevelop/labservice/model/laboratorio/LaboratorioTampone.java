package com.uniba.di.dfmdevelop.labservice.model.laboratorio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uniba.di.dfmdevelop.labservice.model.Prenotazione;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@Entity(name = "laboratorio_tampone")
public class LaboratorioTampone {

    @EmbeddedId
    private TamponePrezzoKey tamponePrezzoKey;

    @ManyToOne
    @MapsId("laboratorioId")
    @JoinColumn(name = "laboratorio_id")
    @JsonIgnore
    private Laboratorio laboratorio;

    @ManyToOne
    @MapsId("tamponeId")
    @JoinColumn(name = "tampone_id")
    private Tampone tampone;

    @Column(
            columnDefinition = "Decimal(5,2)"
    )
    private double prezzo;

    @OneToMany(mappedBy = "laboratorioTampone", cascade = CascadeType.ALL)
    private Collection<Prenotazione> listaPrenotazioni = new ArrayList<>();

    public LaboratorioTampone(Laboratorio laboratorio, Tampone tampone, double prezzo) {
        this.tamponePrezzoKey = new TamponePrezzoKey(laboratorio.getId(),tampone.getId());
        this.laboratorio = laboratorio;
        this.tampone = tampone;
        this.prezzo = prezzo;
    }
}
