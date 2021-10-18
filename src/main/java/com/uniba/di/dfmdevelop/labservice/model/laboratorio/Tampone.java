package com.uniba.di.dfmdevelop.labservice.model.laboratorio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tampone")
public class Tampone {

    @Id
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "nome",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String nome;

    @OneToMany(mappedBy = "tampone", cascade= CascadeType.ALL)
    @JsonIgnore
    private Collection<LaboratorioTampone> listaLaboratori = new ArrayList<>();
}
