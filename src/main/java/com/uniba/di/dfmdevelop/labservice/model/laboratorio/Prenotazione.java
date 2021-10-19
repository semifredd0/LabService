package com.uniba.di.dfmdevelop.labservice.model.laboratorio;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "prenotazione")
public class Prenotazione {

    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_sequence" )
    @Column(
            name = "id",
            updatable = false )
    private Long id;

    @Column(
            name = "codice_prenotazione",
            nullable = false,
            columnDefinition = "TEXT" )
    private String codPrenotazione;

    @Column(
            name = "data_prenotazione",
            nullable = false,
            columnDefinition = "DATE" )
    private Date dataPrenotazione;

    @Column(
            name = "fascia_oraria",
            nullable = false,
            columnDefinition = "TEXT" )
    private String orario;

    @Column(
            name = "stato_pagamento",
            nullable = false,
            columnDefinition = "BOOLEAN" )
    private boolean statusPagamento;

    public Prenotazione() {
    }

    public Prenotazione(Long id, String codPrenotazione, Date dataPrenotazione, String orario, boolean statusPagamento) {
        this.id = id;
        this.codPrenotazione = codPrenotazione;
        this.dataPrenotazione = dataPrenotazione;
        this.orario = orario;
        this.statusPagamento = statusPagamento;
    }
}
