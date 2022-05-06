package com.example.chatroom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tilkobling {
    static final int PORT = 8000;
    static final String HOST = "localhost"; // Dima sin offentlige IP
    static ObjectInputStream innStrøm;
    static ObjectOutputStream utStrøm;
    Socket socket;
    public Tilkobling () {
        new Thread(() -> {
            try {
                System.out.println("Tråden for tilkobling blir opprettet");
                socket = new Socket(HOST, PORT);
                utStrøm = new ObjectOutputStream(socket.getOutputStream());
                innStrøm = new ObjectInputStream(socket.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("Klarte ikke koble til");
            }
        }).start();
    }
    public void lukkTilkobling() {
        try {
            utStrøm.close();
            innStrøm.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Feil ved lukking av tilkobling");
        }
    }
    public void sendMelding(String tekst, String brukerNavn) {
        Map<Object, Object> meldingMap = new HashMap<>();
        meldingMap.put(tekst, brukerNavn);
        try {
            utStrøm.writeObject(meldingMap);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Feil ved sending av melding til server");
        }
    }

    public ObservableList<String> getRom() {
        ObservableList<String> liste = FXCollections.observableArrayList();
        try {
            HashMap<Object, Object> query = new HashMap<>();
            query.put("query", "getRom");
            utStrøm.writeObject(query);
            ArrayList innListe = (ArrayList) innStrøm.readObject();
            // lag liste om til ObservableList
            return liste;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return liste;
    }
}
