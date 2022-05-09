package com.example.v22klient;

public class Bruker {
    String brukernavn, epost, tlf;

    public Bruker(String brukernavn, String epost, String tlf) {
        this.brukernavn = brukernavn;
        this.epost = epost;
        this.tlf = tlf;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
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
                "brukernavn='" + brukernavn + '\'' +
                ", epost='" + epost + '\'' +
                ", tlf='" + tlf + '\'' +
                '}';
    }
}
