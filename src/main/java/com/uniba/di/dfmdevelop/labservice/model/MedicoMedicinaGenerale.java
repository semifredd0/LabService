package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "medico_medicina_generale")
public class MedicoMedicinaGenerale {

    @Id
    @SequenceGenerator(
            name = "medico_sequence",
            sequenceName = "medico_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "medico_sequence" )
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
            name = "specializzazione",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String specializzazione;

    @Column(
            name = "indirizzo_stradale_studio",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String indirizzoStudio;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "id_utente"
    )
    private UtenteGenerico utenteGenerico;

    public MedicoMedicinaGenerale() {}
}