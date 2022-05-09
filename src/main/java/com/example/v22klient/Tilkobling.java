package com.example.v22klient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;


public class Tilkobling {
    // Etablerer variabler for ObjectStreams som vil bli benyttet til overføring av data mellom klient og tjener
    private Socket socket;
    private ObjectInputStream innStrøm;
    private ObjectOutputStream utStrøm;
    private Bruker bruker;

    // Oppretter tilkobling
    public Tilkobling(Socket socket) throws IOException {
        try {
            this.socket = socket;
            this.utStrøm = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // Forsøker å lukke strømmene
            innStrøm.close();
            utStrøm.close();
            socket.close();
        }
    }

    // Logger inn bruker
    public void leggInnBruker() {
        try {
            System.out.println("Startet leggInnBruker");
            // Oppretter og sender HashMap med brukerinfo
            HashMap<Object, Object> brukerMap = new HashMap<>();
            brukerMap.put("query", "loggInn");
            brukerMap.put("brukernavn", bruker.getBrukernavn());
            brukerMap.put("epost", bruker.getEpost());
            brukerMap.put("tlf", bruker.getTlf());
            utStrøm.writeObject(brukerMap);
            // Tar imot og behandler svar fra tjeneren
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
    // Overlastet metode for å oppdatere brukervariabel før kall på leggInnBruker()
    public void loggInnBruker(Bruker bruker) {
        this.bruker = bruker;
        leggInnBruker();
    }

    // Send data
    public void sendRekke() {
        System.out.println("Har ikke laget sendRekke()");
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

}
