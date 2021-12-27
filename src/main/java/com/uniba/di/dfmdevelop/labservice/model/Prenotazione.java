package com.uniba.di.dfmdevelop.labservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uniba.di.dfmdevelop.labservice.model.cittadino.UtenteEsterno;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.LaboratorioTampone;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
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

    // Possono essere cittadini non registrati, assistiti oppure dipendenti
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_esterno_id")
    private UtenteEsterno utenteEsterno;

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

    @Column(
            name = "pagamento_online",
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean pagamentoOnline;
}
