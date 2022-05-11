package com.example.v22klient;

import java.util.ArrayList;
import java.util.Collections;

public class Rekke {
    public ArrayList<Integer> tallRekke;
    public int innsats;
    protected double gevinst;


    public Rekke(ArrayList<Integer>rekkeMedTall, int rekkePris, Bruker bruker) {
        this.tallRekke = new ArrayList<>(rekkeMedTall);
        Collections.sort(tallRekke);
        this.innsats= rekkePris;
        bruker.innsatsListe.add(this.innsats);
        bruker.rekkeListe.add(this.tallRekke);
        System.out.println("Ny rekke laget:" + this);
    }

    @Override
    public String toString() {
        return "Rekke{" +
                "tallRekke=" + tallRekke +
                ", innsats=" + innsats +
                ", gevinst=" + gevinst +
                '}';
    }

    public void setGevinst(double gevinst) {
        this.gevinst = gevinst;
    }
}
