package com.example.chatroom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Rom {
    private String navn;
    protected static ObservableList<Rom> romliste = FXCollections.observableArrayList();


    public Rom (String romnavn) {
        this.navn = romnavn;
    }

    @Override
    public String toString() {return navn;}

    public static void oppdaterListe(ArrayList<String> romStringListe) {
        romliste.clear();
        romStringListe.forEach((romString) -> {
            romliste.add(new Rom(romString));
        });
    }

    public static void setRomliste(ObservableList<Rom> romliste) {
        Rom.romliste = romliste;
    }

    public static ObservableList<Rom> getRomliste() {
        Tilkobling.hentRomListeFraServer();
        return romliste;
    }


}
