package com.example.chatroom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Rom {
    private String navn;
    private static ObservableList<Rom> romliste = FXCollections.observableArrayList();
    public Rom (String romnavn) {
        this.navn = romnavn;
    }
    @Override
    public String toString() {return navn;}
    public static void oppdaterListe(ArrayList<String> romStringListe) {
        System.out.println(romStringListe);
        System.out.println(romliste);
        romliste.removeAll();
        romStringListe.forEach((romString) -> {
            romliste.add(new Rom(romString));
        });
        System.out.println(romliste);
    }
    public static void setRomliste(ObservableList<Rom> romliste) {
        Rom.romliste = romliste;
    }
    public static ObservableList<Rom> getRomliste() {
        Tilkobling.hentRomListeFraServer();
        return romliste;
    }
}
