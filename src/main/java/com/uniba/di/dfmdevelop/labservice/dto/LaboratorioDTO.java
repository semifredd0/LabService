package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
@ToString
public class LaboratorioDTO extends UtenteGenericoDTO {

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String nomeLaboratorio;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Il campo deve contenere esattamente 10 caratteri")
    private String numeroTelefono;

    @NotEmpty
    @Size(min = 2, message = "Il campo deve contenere almeno 2 caratteri")
    private String indirizzoStradale;

    @NotEmpty
    @Size(min = 27, max = 27, message = "Il campo deve contenere esattamente 27 caratteri")
    private String codiceIban;

    @NotEmpty
    @Size(min = 11, max = 11, message = "Il campo deve contenere esattamente 11 caratteri")
    private String partitaIva;

    // Memorizza i tamponi inseriti in fase di registrazione
    private boolean molecolare = false;
    private boolean antigenico = false;
    private boolean sierologico = false;
    private double prezzo_molecolare;
    private double prezzo_antigenico;
    private double prezzo_sierologico;

    // Calendario laboratorio
    private boolean lunedi = false;
    private boolean martedi = false;
    private boolean mercoledi = false;
    private boolean giovedi = false;
    private boolean venerdi = false;
    private boolean sabato = false;
    private boolean domenica = false;
    private GiornoLavorativoDTO orario_lunedi = new GiornoLavorativoDTO();
    private GiornoLavorativoDTO orario_martedi = new GiornoLavorativoDTO();
    private GiornoLavorativoDTO orario_mercoledi = new GiornoLavorativoDTO();
    private GiornoLavorativoDTO orario_giovedi = new GiornoLavorativoDTO();
    private GiornoLavorativoDTO orario_venerdi = new GiornoLavorativoDTO();
    private GiornoLavorativoDTO orario_sabato = new GiornoLavorativoDTO();
    private GiornoLavorativoDTO orario_domenica = new GiornoLavorativoDTO();

    public LaboratorioDTO() {
        super();
    }

    public String calendarioToString() {
        String calendario = "Lunedi: " + (lunedi ? orario_lunedi.toString() : "Chiuso");
        calendario += "\nMartedi: " + (martedi ? orario_martedi.toString() : "Chiuso");
        calendario += "\nMercoledi: " + (mercoledi ? orario_mercoledi.toString() : "Chiuso");
        calendario += "\nGiovedi: " + (giovedi ? orario_giovedi.toString() : "Chiuso");
        calendario += "\nVenerdi: " + (venerdi ? orario_venerdi.toString() : "Chiuso");
        calendario += "\nSabato: " + (sabato ? orario_sabato.toString() : "Chiuso");
        calendario += "\nDomenica: " + (domenica ? orario_domenica.toString() : "Chiuso");
        return calendario;
    }
}