package com.example.chatroom;

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
                sjekkInnBruker();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("Klarte ikke koble til");
            }
        }).start();
    }
    // TODO: Metode som skal sende brukernavn til server og sjekke om det er tilgjengelig.
    private void sjekkInnBruker() {
        System.out.println("Starter sjekkInnBruker");
        Map<Object, Object> brukerMap = new HashMap<>();
        brukerMap.put("query", "sjekkInnBruker");
        brukerMap.put("brukernavn", Main.getBrukerNavn());
        try {
            utStrøm.writeObject(brukerMap);
            System.out.println(brukerMap);
            System.out.println("Sendt brukerMap");
            Map input = (Map) innStrøm.readObject();
            if ((int) input.get("status") == 1) {
                ArrayList<String> tempRomListe = (ArrayList<String>) input.get("romliste");
                System.out.println("Lagt til rom");
                tempRomListe.forEach(Rom::new);
            } else if ((int) input.get("status") == 0) {
                System.out.println("Bruker allerede tatt");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Feil oppstått ved innsjekking av bruker");
        }
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
        meldingMap.put("query", "sendMelding");
        meldingMap.put("tekst", tekst);
        meldingMap.put("brukernavn", brukerNavn);
        try {
            utStrøm.writeObject(meldingMap);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Feil ved sending av melding til server");
        }
    }

    public void getRom() {
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
        });
    }

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
