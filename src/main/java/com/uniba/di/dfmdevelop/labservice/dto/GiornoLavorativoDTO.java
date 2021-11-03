package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GiornoLavorativoDTO {
    private String aperturaMattina;
    private String chiusuraMattina;
    private String aperturaPomeriggio;
    private String chiusuraPomeriggio;

    public String getAperturaMattina() {
        return aperturaMattina;
    }

    public String getChiusuraMattina() {
        return chiusuraMattina;
    }

    public String getAperturaPomeriggio() {
        return aperturaPomeriggio;
    }

    public String getChiusuraPomeriggio() {
        return chiusuraPomeriggio;
    }

    public void setAperturaMattina(String aperturaMattina) {
        this.aperturaMattina = aperturaMattina;
    }

    public void setChiusuraMattina(String chiusuraMattina) {
        this.chiusuraMattina = chiusuraMattina;
    }

    public void setAperturaPomeriggio(String aperturaPomeriggio) {
        this.aperturaPomeriggio = aperturaPomeriggio;
    }

    public void setChiusuraPomeriggio(String chiusuraPomeriggio) {
        this.chiusuraPomeriggio = chiusuraPomeriggio;
    }

    @Override
    public String toString() {
        if(aperturaMattina.length()==0) aperturaMattina = "[Indefinito]";
        if(chiusuraMattina.length()==0) chiusuraMattina = "[Indefinito]";
        if(aperturaPomeriggio.length()==0) aperturaPomeriggio = "[Indefinito]";
        if(chiusuraPomeriggio.length()==0) chiusuraPomeriggio = "[Indefinito]";
        return "Mattina: " + aperturaMattina + " - " + chiusuraMattina +
                " / Pomeriggio: " + aperturaPomeriggio + " - " + chiusuraPomeriggio;
    }
}