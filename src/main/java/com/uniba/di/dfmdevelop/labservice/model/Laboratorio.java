package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity(name = "laboratorio")
public class Laboratorio {

    @Id
    @SequenceGenerator(
            name = "lab_sequence",
            sequenceName = "lab_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lab_sequence" )
    @Column(
            name = "id",
            updatable = false )
    private Long id;

    @Column(
            name = "nome_laboratorio",
            nullable = false,
            columnDefinition = "TEXT" )
    private String nome;

    @Column(
            name = "numero_telefono",
            nullable = false,
            columnDefinition = "TEXT" )
    private String telefono;

    @Column(
            name = "indirizzo_stradale",
            nullable = false,
            columnDefinition = "TEXT" )
    private String indirizzo;

    @Column(
            name = "codice_iban",
            nullable = false,
            columnDefinition = "TEXT" )
    private String IBAN;

    @Column(
            name = "partita_iva",
            nullable = false,
            columnDefinition = "TEXT" )
    private String partitaIVA;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "id_utente"
    )
    private UtenteGenerico utenteGenerico;

    public Laboratorio() {}

    public Laboratorio(String nome,
                       String telefono,
                       String indirizzo,
                       String IBAN,
                       String partitaIVA,
                       UtenteGenerico utenteGenerico) {
        this.nome = nome;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.IBAN = IBAN;
        this.partitaIVA = partitaIVA;
        this.utenteGenerico = utenteGenerico;
    }
}
