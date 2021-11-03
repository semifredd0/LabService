package com.uniba.di.dfmdevelop.labservice.model.laboratorio;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "calendario")
public class Calendario {

    @Id
    @SequenceGenerator(
            name = "cal_sequence",
            sequenceName = "cal_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cal_sequence" )
    @Column(
            name = "id",
            updatable = false )
    private Long id;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "lunedi"
    )
    private GiornoLavorativo lunedi;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "martedi"
    )
    private GiornoLavorativo martedi;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "mercoledi"
    )
    private GiornoLavorativo mercoledi;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "giovedi"
    )
    private GiornoLavorativo giovedi;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "venerdi"
    )
    private GiornoLavorativo venerdi;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "sabato"
    )
    private GiornoLavorativo sabato;

    @OneToOne(
            // fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "domenica"
    )
    private GiornoLavorativo domenica;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "calendario"
    )
    private Laboratorio laboratorio;

    public Calendario() {}

    public Calendario(GiornoLavorativo lunedi,
                      GiornoLavorativo martedi,
                      GiornoLavorativo mercoledi,
                      GiornoLavorativo giovedi,
                      GiornoLavorativo venerdi,
                      GiornoLavorativo sabato,
                      GiornoLavorativo domenica) {
        this.lunedi = lunedi;
        this.martedi = martedi;
        this.mercoledi = mercoledi;
        this.giovedi = giovedi;
        this.venerdi = venerdi;
        this.sabato = sabato;
        this.domenica = domenica;
    }
}
