package com.example.chatroom;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Tilkobling {
    static final int PORT = 8000;
    static final String HOST = "localhost"; // Dima sin offentlige IP
    static ObjectInputStream innStrøm;
    static ObjectOutputStream utStrøm;
    static ObservableList<Rom> romOL;
    Socket socket;
    public Tilkobling () {
        new Thread(() -> {
            try {
                socket = new Socket(HOST, PORT);
                utStrøm = new ObjectOutputStream(socket.getOutputStream());
                innStrøm = new ObjectInputStream(socket.getInputStream());
                System.out.println("Opprettet tilkobling til server");
                if (sjekkBrukernavn()) {
                    hentRom();
                } else {
                    throw new RuntimeException("Brukernavnsjekk feilet");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("Klarte ikke koble til");
            }
        }).start();
    }

    // TODO: Lage brukernavnsjekk som skal sende bruker og returnere om navnet er lovlig.
    private boolean sjekkBrukernavn() {
        HashMap<Object, Object> sendBrukerMap = new HashMap<>();
        sendBrukerMap.put("brukernavn", Main.getBrukernavn());
        try {
            utStrøm.writeObject(sendBrukerMap);
            Boolean suksess = (Boolean) innStrøm.readObject();
            utStrøm.close();
            return suksess;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void lukkTilkobling() {
        try {
            socket.close();
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

    public void hentRom() {
        new Thread(() -> {
            try {
                System.out.println("før lesing av innStrøm");
                System.out.println(innStrøm.readObject());
                System.out.println("etter lesing av innStrøm");
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    // TODO: Denne metoden under her skal benyttes for å bygge/hente ut en observablelist av rom
    //public ObservableList<String> getRom() {
    //    ObservableList<String> liste = FXCollections.observableArrayList();
    //    try {
    //        HashMap<Object, Object> query = new HashMap<>();
    //        query.put("query", "getRom");
    //        utStrøm.writeObject(query);
    //       ArrayList innListe = (ArrayList) innStrøm.readObject();
    //        // lag liste om til ObservableList
    //        return liste;
    //   } catch (IOException | ClassNotFoundException e) {
    //       System.out.println(e.getMessage());
    //   }
        //    return liste;
    //}
}
