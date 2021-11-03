package com.uniba.di.dfmdevelop.labservice.model.laboratorio;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "giorno_lavorativo")
public class GiornoLavorativo {

    @Id
    @SequenceGenerator(
            name = "giorno_sequence",
            sequenceName = "giorno_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "giorno_sequence" )
    @Column(
            name = "id",
            updatable = false )
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "lunedi"
    )
    private Calendario lunedi;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "martedi"
    )
    private Calendario martedi;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "mercoledi"
    )
    private Calendario mercoledi;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "giovedi"
    )
    private Calendario giovedi;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "venerdi"
    )
    private Calendario venerdi;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "sabato"
    )
    private Calendario sabato;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "domenica"
    )
    private Calendario domenica;

    @Column(
            name = "apertura_mattina",
            columnDefinition = "TEXT" )
    private String aperturaMattina;

    @Column(
            name = "chiusura_mattina",
            columnDefinition = "TEXT" )
    private String chiusuraMattina;

    @Column(
            name = "apertura_pomeriggio",
            columnDefinition = "TEXT" )
    private String aperturaPomeriggio;

    @Column(
            name = "chiusura_pomeriggio",
            columnDefinition = "TEXT" )
    private String chiusuraPomeriggio;

    public GiornoLavorativo() {}

    public GiornoLavorativo(String aperturaMattina,
                            String chiusuraMattina,
                            String aperturaPomeriggio,
                            String chiusuraPomeriggio) {
        this.aperturaMattina = aperturaMattina;
        this.chiusuraMattina = chiusuraMattina;
        this.aperturaPomeriggio = aperturaPomeriggio;
        this.chiusuraPomeriggio = chiusuraPomeriggio;
    }
}