package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity(name = "dettagli-pagamento")
public class PaymentDetail {

    @Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "payment_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "valuta",
            nullable = false,
            columnDefinition = "TEXT" )
    private String currency;

    @Column(
            name = "valuta",
            nullable = false,
            columnDefinition = "DOUBLE" )
    private Double amount;

    @Column(
            name = "metodo_pagamento",
            nullable = false,
            columnDefinition = "TEXT" )
    private String method;

    @Column(
            name = "descrizione",
            nullable = false,
            columnDefinition = "TEXT" )
    private String description;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "id_utente"
    )
    private UtenteGenerico utenteGenerico;

    public PaymentDetail() {
    }

    public PaymentDetail(String currency,
                         Double amount,
                         String method,
                         String description,
                         UtenteGenerico utenteGenerico) {
        this.currency = currency;
        this.amount = amount;
        this.method = method;
        this.description = description;
        this.utenteGenerico = utenteGenerico;
    }
}
