package com.example.chatroom;

import java.util.ArrayList;

public class Rom {
    private String navn;
    private static ArrayList<Rom> romliste = new ArrayList<>();
    public Rom (String romnavn) {
        this.navn = romnavn;
        romliste.add(this);
    }
    public ArrayList<Rom> getRomliste() {return romliste;}
}
