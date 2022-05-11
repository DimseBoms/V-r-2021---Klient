package com.example.v22klient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;


public class Tilkobling {
    // Tilkoblingsvariabler
    public final String IP = "localhost";
    public final int PORT = 8000;
    // Etablerer variabler for ObjectStreams som vil bli benyttet til overføring av data mellom klient og tjener
    private Socket socket;
    private ObjectInputStream innStrøm;
    private ObjectOutputStream utStrøm;
    private Bruker bruker;
    private boolean brukerLoggetInn = false;

    // Oppretter tilkobling
    public Tilkobling() throws IOException {
        System.out.println("Forsøker å koble til");
        try {
            this.socket = new Socket(IP, PORT);
            System.out.println("Koblet til tjener " + socket);
            this.utStrøm = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // Forsøker å lukke strømmene
            utStrøm.close();
            socket.close();
        }
    }

    // Logger inn bruker
    public void loggInnBruker() {
        try {
            System.out.println("Startet loggInnBruker");
            // Oppretter og sender HashMap med brukerinfo
            HashMap<Object, Object> brukerMap = new HashMap<>();
            brukerMap.put("query", "loggInn");
            brukerMap.put("fornavn", bruker.getFornavn());
            brukerMap.put("etternavn", bruker.getEtternavn());
            brukerMap.put("epost", bruker.getEpost());
            brukerMap.put("tlf", bruker.getTlf());
            System.out.println("Sender innloggingsforsøk til serveren " + brukerMap);
            utStrøm.writeObject(brukerMap);
            // Tar imot og behandler svar fra tjeneren
            this.innStrøm = new ObjectInputStream(socket.getInputStream());
            HashMap<Object, Object> svar = (HashMap<Object, Object>) innStrøm.readObject();
            System.out.println("Svar fra tjener " + svar.toString());
            if ((int) svar.get("feilkode") == 0) {
                this.brukerLoggetInn = true;
            }
        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, innStrøm, utStrøm);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    // Overlastet metode for å oppdatere brukervariabel før kall på leggInnBruker()
    public void loggInnBruker(Bruker bruker) {
        this.bruker = bruker;
        loggInnBruker();
    }

    // Send data
    public void sendRekke(Bruker bruker) {
        try {
            // Oppretter og sender HashMap med bruker sine rekker
            System.out.println("Sender rekker:");
            System.out.println("Rekkeliste: " + bruker.rekkeListe);
            System.out.println("Satsliste: " + bruker.innsatsListe);
            HashMap<Object, Object> brukerMap = new HashMap<>();
            brukerMap.put("query", "sendRekke");
            brukerMap.put("rekker", bruker.rekkeListe);
            brukerMap.put("innsats", bruker.innsatsListe);
            utStrøm.writeObject(brukerMap);
            // Tar imot og behandler svar fra tjeneren
            System.out.println("Venter på respons fra server med svar på rekker...");
            this.innStrøm = new ObjectInputStream(socket.getInputStream());
            HashMap<Object, Object> svar = (HashMap<Object, Object>) innStrøm.readObject();
            System.out.println(svar.toString());
        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, innStrøm, utStrøm);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Hjelpemetode for å lukke alle åpne strømmer og sockets
    public void closeEverything(Socket socket, ObjectInputStream innStrøm, ObjectOutputStream utStrøm) {
        try {
            if (innStrøm != null) {
                innStrøm.close();
            }
            if (utStrøm != null) {
                utStrøm.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean brukerLoggetInn() {
        return brukerLoggetInn;
    }

    public Bruker getBruker() {
        return bruker;
    }
}
