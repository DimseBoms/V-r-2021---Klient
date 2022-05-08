package com.example.chatroom;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

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
    private static String brukernavn = "";
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
                velgBrukernavnDialog(); // ER DET FØRSTE SOM MÅ SKJE. BRUKER FÅR LISTE MED ROM SOM RESPONS
  //              opprettRom(); // REQUEST FRA BRUKER OM Å OPPRETTE NYTT ROM
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("Klarte ikke koble til");
            }
        }).start();
    }
    public static void setBrukernavn(String brukernavn) {
        Tilkobling.brukernavn = brukernavn;
    }
    /**
     * Metode for å sende request om nytt rom (create) til server
     */
    protected void opprettRom() {
        System.out.println("Starter metoden opprettRom");
        Map<Object, Object> brukerMap = new HashMap<>();
        brukerMap.put("query", "opprettRom");
        brukerMap.put("brukernavn", brukernavn);
        brukerMap.put("rom", brukernavn);
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
                System.out.println("Romnavn eksisterer allerede");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Feil oppstått ved innsjekking av bruker");
        }
    }
    // Sjekker inn bruker på server og mottar romliste dersom innsjekking er OK
    // TODO: Må separere metoden og vente på brukernavn inn
    private void sjekkInnBruker() {
        System.out.println("Starter sjekkInnBruker");
        Map<Object, Object> brukerMap = new HashMap<>();
        brukerMap.put("query", "sjekkInnBruker");
        brukerMap.put("brukernavn", brukernavn);
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
                brukernavnIkkeTilgjengelig();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Feil oppstått ved innsjekking av bruker");
        }
    }
    private void velgBrukernavnDialog() {
        Platform.runLater(() -> {
            TextInputDialog inputdialog = new TextInputDialog("");
            inputdialog.setContentText("Brukernavn: ");
            inputdialog.setHeaderText("Innlogging");
            inputdialog.showAndWait();
            Tilkobling.setBrukernavn(inputdialog.getEditor().getText());
            GuiKonstruktør.oppdaterBrukernavn();
            sjekkInnBruker();
        });
    }
    private void brukernavnIkkeTilgjengelig() {
        Platform.runLater(() -> {
            Alert utilgjengeligBrukerAlert = new Alert(Alert.AlertType.ERROR);
            utilgjengeligBrukerAlert.setHeaderText("Brukernavn utilgjengelig");
            utilgjengeligBrukerAlert.showAndWait();
            velgBrukernavnDialog();
        });
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
    public String getBrukernavn() {
        return brukernavn;
    }
}
