package com.uniba.di.dfmdevelop.labservice.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GiornoLavorativo {
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
        return "Mattina: " + aperturaMattina + " - " + chiusuraMattina +
                " / Pomeriggio: " + aperturaPomeriggio + " - " + chiusuraPomeriggio;
    }
}