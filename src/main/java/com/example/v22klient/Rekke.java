package com.example.v22klient;

import java.lang.reflect.Array;

public class Rekke {
    final static int LENGDE = 6;
    Array[] rekke;
    int sats;

    public Rekke(Array[] rekke, int sats) {
        if (rekke.length == 6) {
            this.rekke = rekke;
            this.sats = sats;
        } else throw new IllegalArgumentException("Rekke-objekt må ha syv verdier i rekka.");
    }
}
