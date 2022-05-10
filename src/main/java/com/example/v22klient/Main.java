package com.example.v22klient;

import java.io.IOException;
import java.net.Socket;

public class Main {
    // For øyeblikket kun en testklasse for å se om kommunikasjon mellom klient og tjener fungerer
    public static void main(String[] args) throws IOException {

        // Lager en testbruker
        Bruker testbruker = new Bruker("testFornavn", "testEtternavn", "testpost@epost.no", "12345678");
        System.out.println("Laget testbruker" + testbruker);
        // Lager en socket for å koble til tjener
        Socket socket = new Socket("localhost", 8000);
        // Lager tilkobling og sender socket til tilkoblingen
        Tilkobling klient = new Tilkobling();
        // Sender bruker over til tilkobling og forsøker innlogging
        klient.loggInnBruker(testbruker);
    }

}
