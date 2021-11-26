package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "cittadino")
public class Cittadino {

    @Id
    @SequenceGenerator(
            name = "cittadino_sequence",
            sequenceName = "cittadino_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cittadino_sequence" )
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
            name = "codice_fiscale",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String codFiscale;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "id_utente"
    )
    private UtenteGenerico utenteGenerico;

    public Cittadino() {
    }

    public Cittadino(String nome,
                     String cognome,
                     LocalDate dataNascita,
                     String numeroTelefono,
                     String codFiscale,
                     UtenteGenerico utenteGenerico) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.numeroTelefono = numeroTelefono;
        this.codFiscale = codFiscale;
        this.utenteGenerico = utenteGenerico;
    }
}