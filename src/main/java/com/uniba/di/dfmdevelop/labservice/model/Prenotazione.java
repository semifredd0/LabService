package com.uniba.di.dfmdevelop.labservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity(name = "prenotazione")
public class Prenotazione {

    @Id
    @SequenceGenerator(
            name = "prenotazione_sequence",
            sequenceName = "prenotazione_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "prenotazione_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private UtenteGenerico utenteGenerico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "laboratorio", nullable = false),
            @JoinColumn(name = "tampone", nullable = false)
    })
    @JsonIgnore
    private LaboratorioTampone laboratorioTampone;

    @Column(
            name = "data_prenotazione",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate dataPrenotazione;
}
