package com.example.v22klient;

import java.util.ArrayList;

public class Bruker {
    protected  ArrayList<ArrayList<Integer>> rekkeListe;
    protected ArrayList<Integer> innsatsListe;
    protected String fornavn, etternavn, epost, tlf;

    /**
     * Inneholder informasjonen om bruker
     * @param fornavn
     * @param etternavn
     * @param epost
     * @param tlf
     */
    public Bruker(String fornavn, String etternavn, String epost, String tlf) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.epost = epost;
        this.tlf = tlf;
        this.rekkeListe = new ArrayList<>();
        this.innsatsListe = new ArrayList<>();
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
