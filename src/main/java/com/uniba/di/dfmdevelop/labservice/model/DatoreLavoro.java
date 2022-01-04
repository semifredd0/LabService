package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity( name = "datore_lavoro")
public class DatoreLavoro {

    @Id
    @SequenceGenerator(
            name = "datore_sequence",
            sequenceName = "datore_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "datore_sequence" )
    @Column(
            name = "id",
            updatable = false )
    private Long id;

    @Column(
            name = "nome",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String nome;

    @Column(
            name = "cognome",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String cognome;

    @Column(
            name = "data_nascita",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate dataNascita;

    @Column(
            name = "numero_telefono",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String numeroTelefono;

    @Column(
            name = "nome_azienda",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String nomeAzienda;

    @Column(
            name = "indirizzo_stradale_azienda",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String indirizzoAzienda;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "id_utente"
    )
    private UtenteGenerico utenteGenerico;

    public DatoreLavoro() {}
}
