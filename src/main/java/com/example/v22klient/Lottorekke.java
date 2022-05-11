package com.example.v22klient;

import javafx.scene.Node;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Lottorekke extends Node {

    private int brukerId; // denne burde komme fra bruker? Bruker bruker?
    private Timestamp tidspunkt; // Jeg er en util.Date
    private int antallRette;
    private int t1;
    private int t2;
    private int t3;
    private int t4;
    private int t5;
    private int t6;
    private int t7;
    private static ArrayList<Lottorekke> rekker = new ArrayList<>();

    public Lottorekke(int brukerId, Timestamp tidspunkt, int[] rekke) {
        this.brukerId = brukerId;
        this.tidspunkt = tidspunkt;

        rekker.add(this);
    }

    public static ArrayList<Lottorekke> getLottorekker() {
        return rekker;
    }

    @Override
    public String toString() {
        return "Lottorekke{" +
                "brukerId=" + brukerId +
                ", tidspunt=" + tidspunkt +
                ", antallRette=" + antallRette +
                ", t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                ", t4=" + t4 +
                ", t5=" + t5 +
                ", t6=" + t6 +
                ", t7=" + t7 +
                '}';
    }
}
