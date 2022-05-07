package com.example.chatroom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Rom {
    @Override
    public String toString() {return navn;}

    private String navn;
    private static ObservableList<Rom> romliste = FXCollections.observableArrayList();
    public Rom (String romnavn) {
        this.navn = romnavn;
        romliste.add(this);
    }
    public static ObservableList<Rom> getRomliste() {return romliste;}
}
