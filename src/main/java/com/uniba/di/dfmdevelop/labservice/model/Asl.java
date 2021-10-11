package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "asl")
public class Asl {

    @Id
    @SequenceGenerator(
            name = "asl_sequence",
            sequenceName = "asl_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "asl_sequence" )
    @Column(
            name = "id",
            updatable = false )
    private Long id;

    @Column(
            name = "zona_asl",
            nullable = false,
            columnDefinition = "TEXT" )
    private String zonaRiferimento;

    @Column(
            name = "email",
            nullable = false,
            columnDefinition = "TEXT" )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT" )
    private String password;

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
            name = "partita_iva",
            nullable = false,
            columnDefinition = "TEXT" )
    private String partitaIva;

    public Asl() {
    }

    public Asl(Long id,
               String zonaRiferimento,
               String email,
               String password,
               String telefono,
               String indirizzo,
               String partitaIva) {
        this.id = id;
        this.zonaRiferimento = zonaRiferimento;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.indirizzo = indirizzo;
        this.partitaIva = partitaIva;
    }
}
