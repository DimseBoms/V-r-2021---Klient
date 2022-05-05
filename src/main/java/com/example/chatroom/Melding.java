package com.example.chatroom;

import java.text.SimpleDateFormat;
import java.util.Date;

//https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html

public class Melding {
    //Variabler
    private String tekst;
    private String date;
    private String brukerNavn;

    //Konstruktør
    public Melding(String tekst, String brukerNavn) {
        setTekst(tekst);
        setTime();
        setBrukerNavn(brukerNavn);
    }

    //Set-Metoder
    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public void setBrukerNavn(String brukerNavn) {
        this.brukerNavn = brukerNavn;
    }

    public void setTime() {

        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        this.date = date;
    }

    //Get-Metoder
    public String getTekst() {
        return tekst;
    }

    public String getBrukerNavn() {
        return brukerNavn;
    }

    public String getDate() {
        return date;
    }

    //Metoder
}
