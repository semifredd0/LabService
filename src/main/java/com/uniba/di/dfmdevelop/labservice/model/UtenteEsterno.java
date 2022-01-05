package com.uniba.di.dfmdevelop.labservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/*
    Rappresenta utenti esterni per cui è stato prenotato un tampone
    da parte di un cittadino registrato.
    Inoltre questa Entity viene utilizzata per rappresentare assistiti
    e dipendenti per cui i medici e i datori di lavoro possono prenotare tamponi.
    Se si tratta di dipendenti, il campo Datore contiene l'ID del datore di lavoro
    a cui fa riferimento il dipendente. Altrimenti il campo = NULL.
 */

@Data
@NoArgsConstructor
@Entity(name = "utente_esterno")
public class UtenteEsterno {

    @Id
    @SequenceGenerator(
            name = "esterno_sequence",
            sequenceName = "esterno_sequence",
            allocationSize = 1 )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "esterno_sequence" )
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "datore_id")
    private DatoreLavoro datoreLavoro;

    public UtenteEsterno(String nome,
                     String cognome,
                     LocalDate dataNascita,
                     String numeroTelefono,
                     String codFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.numeroTelefono = numeroTelefono;
        this.codFiscale = codFiscale;
    }
}