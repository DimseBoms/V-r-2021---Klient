package com.example.v22klient;

public class Bruker {
    String fornavn, etternavn, epost, tlf;

    public Bruker(String fornavn, String etternavn, String epost, String tlf) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.epost = epost;
        this.tlf = tlf;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    @Override
    public String toString() {
        return "Bruker{" +
                "brukernavn='" + fornavn + '\'' +
                ", epost='" + epost + '\'' +
                ", tlf='" + tlf + '\'' +
                '}';
    }
}
