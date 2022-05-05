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
    static final String HOST = "79.161.164.236"; // Dima sin offentlige IP
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
        Map meldingMap = new HashMap<>();
        meldingMap.put(tekst, brukerNavn);
        try {
            utStrøm.writeObject(meldingMap);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Feil ved sending av melding til server");
        }
    }
}
