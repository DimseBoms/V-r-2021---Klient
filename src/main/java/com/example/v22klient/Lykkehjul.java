package com.example.v22klient;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Lykkehjul extends Pane {
    //Her kan man sette størrelse på hjulet. 0 gjør ingen endringer
    private static double setStørrelse = 5;
    //Størrselsen på hjulet blir bestemt av bredden av skjermen
    private static final double WIDTH = KontrollerGUI.WIDTH/2 - setStørrelse;

    //Farge 1 og Farge 2 for feltene
    private static Color farge1 = Color.LIGHTYELLOW;
    private static Color farge2 = Color.CORAL;
    private static final double radius = WIDTH / 2;
    private static double rotasjonSpeed = 10;
    private static double feltGrader, vinnerFeltMin, vinnerFeltMax;
    private static boolean minSpeed, aktivSpin;
    private static int vinnerTall;
    private static int feltAntall;

    private static SequentialTransition sequentialTransition;

    protected Lykkehjul(int feltAntall) {
        //For å sjekke om hjulet er i spill
        this.feltAntall = feltAntall;
        aktivSpin = false;

        //Kalkulerer hvor mange grader hvert felt er
        feltGrader = 360/(double)feltAntall;

        double center = WIDTH/2;
        double feltGraderStart = feltGrader;

        this.setMaxSize(WIDTH, WIDTH);
        this.setMinSize(WIDTH, WIDTH);

        //Lager en stackPane for text
        Group g = new Group();

        double widthOffset = 0;
        double heightOffset = 0;

        //FOR-løkke for å plassere feltene og nummerene
        for (int i = 1; feltAntall>=i; i++) {
            //Lager en arc - Et arc er et felt
            Arc arcFelt = new Arc();

            String nyi = "";
            if (i<10) {
                nyi = "0"+i;
            } else {nyi = ""+i;}

            //Plassere nummer i hjulet
            Text txtNummer = new Text(nyi);
            Font fontTall = Font.font("Arial", FontWeight.BOLD, WIDTH / 20);
            txtNummer.setFont(fontTall);

            //Kalkulerer hvor tallene skal være i feltet
            //Er også godet for å plassere labels midt i istedenfor nederst til venstre
            widthOffset = txtNummer.getBoundsInLocal().getWidth();
            heightOffset = txtNummer.getBoundsInLocal().getHeight();
            txtNummer.setRotate(feltGraderStart);
            txtNummer.setLayoutX(center + radius * Math.sin(i * (2 * Math.PI / feltAntall)));
            txtNummer.setLayoutY(center - radius * Math.cos(i * (2 * Math.PI / feltAntall)));
            txtNummer.setFill(Color.BLACK);

            //Gir farge annenhver
            if (i % 2 == 0) {
                arcFelt.setFill(farge1);
            }
            else {arcFelt.setFill(farge2);}

            //Plasserer feltet i riktig posisjon
            arcFelt.setCenterX(center);
            arcFelt.setCenterY(center);
            arcFelt.setRadiusX(radius + WIDTH/18);
            arcFelt.setRadiusY(radius + WIDTH/18);
            arcFelt.setStartAngle(feltGraderStart);
            arcFelt.setLength(feltGrader);
            arcFelt.setType(ArcType.ROUND);
            arcFelt.setStroke(Color.BLACK);

            //Oppdaterer gradene for neste felt
            feltGraderStart = feltGraderStart + feltGrader;

            //Plasserer feltene i Lykkehjul
            getChildren().add(arcFelt);
            g.getChildren().add(txtNummer);
        }
        g.setLayoutX(g.getLayoutX()-widthOffset/2);
        g.setLayoutY(g.getLayoutY()+heightOffset/2);
        getChildren().add(g);
        Circle midtSirklel = new Circle(center, center, radius /5, Color.GOLDENROD);
        midtSirklel.setStroke(Color.GAINSBORO);
        getChildren().add(midtSirklel);
    }

    //Animasjon for å spinne hjulet
    //Vinnertallet er allerede trukket før hjulet starter å spinne
    EventHandler<ActionEvent> eventSpin = e -> {
        //Kalkulerer hvor mye fart hjulet har mistet
        double friksjonRotasjonSpeed = 0;
        //Kalkulerer hvor langt fra vinnerfeltet hjulet er
        double distanseFraVFelt = this.getRotate() - vinnerFeltMin;
        friksjonRotasjonSpeed = 0.001 * rotasjonSpeed;

        //Kalkulere ny rotasjonSpeed
        rotasjonSpeed = rotasjonSpeed - friksjonRotasjonSpeed;

        //Hvis at minimunspeed har blitt truffet og distansen fra vinnerfeltet er positivt, bruk disaanse til speed istedenfor
        //else fortsette å bruke vanlig rotasjonspeed
        if (minSpeed && distanseFraVFelt > 0) {
            this.setRotate(this.getRotate()-(distanseFraVFelt/1000));
        } else {
            this.setRotate(this.getRotate()-rotasjonSpeed);
        }
        //Hvis at fart er mindre enn 0.05, mimimum fart er true
        if (rotasjonSpeed < 0.5 && !minSpeed && distanseFraVFelt > 70
        || rotasjonSpeed < 0.5 && !minSpeed && getRotate() < -310) {
            minSpeed = true;
        }

        //Resett rotasjonen til innenfor -360 grader
        if (this.getRotate() < -360)
            this.setRotate(0);

        //Hvis at hjulet er over riktig felt og minimumfart er truffet, avslutt rotasjonen
        if (this.getRotate() > vinnerFeltMin && this.getRotate() < vinnerFeltMax && minSpeed) {
            KomponenterGUI.updateLykkeHjulButton(1);
            aktivSpin = false;
            sequentialTransition.stop();
        }
    };

    //Denne eventen gjør at vinnertallet blir funnet etter spinningen
    //Har en fart og sakner ned til den lander på noe
    //Denne eventen gjør at vinnertallet blir funnet etter spinningen
    //Har en fart og sakner ned til den lander på noe
    EventHandler<ActionEvent> eventRandomSpin = e -> {
        //Kalkulerer hvor mye fart hjulet har mistet
        double friksjonRotasjonSpeed = 0.001 * rotasjonSpeed;

        rotasjonSpeed = rotasjonSpeed - friksjonRotasjonSpeed;
        this.setRotate(this.getRotate() - rotasjonSpeed);

        //Resett rotasjonen til innenfor 360 grader
        if (this.getRotate() < -360)
            this.setRotate(0);

        //Hvis hjulet har stoppet, kalkuler hvor hjulet landet
        if (rotasjonSpeed < 0.01) {
            String utTekst = "Vinnertallet er: ";

            //Lager en for-løkke for å sjekke gradene for hver felt
            for (int i = 1; feltAntall>=i; i++) {
                double sjekkVinnerTallGrader = i * -feltGrader;
                double sjekkVinnerTallMaxGrader = sjekkVinnerTallGrader - feltGrader;

                //Hvis at gradene er riktig, sett i til vinnertall
                //Forflytter gradene med en halv feltgrad, fordi den "ekte" nålen er litt til venstre
                if (this.getRotate() < sjekkVinnerTallGrader+(feltGrader/2) && this.getRotate() > sjekkVinnerTallMaxGrader+(feltGrader/2)) {
                    vinnerTall = i;
                }
            }
            System.out.println(utTekst + vinnerTall);
            aktivSpin = false;
            sequentialTransition.stop();
        }
    };


    protected void spin(boolean spinType) {
        //Passer på at hjulet ikke aktiverers flere ganger
        if (!aktivSpin) {
            //Hvis at spinType true, spin normal. False betyr tilfedlig nummer
            sequentialTransition = new SequentialTransition();
            aktivSpin = true;
            minSpeed = false;

            //Rotasjonskraften i begynnelsen av spin
            rotasjonSpeed = new Random().nextInt(10, 20);

            //Finner vinnertall
            Timeline tmSpin;
            if (spinType) {
                tmSpin = new Timeline(new KeyFrame(Duration.millis(1), eventSpin));
                vinnerTall = new Random().nextInt(1, 35);
                System.out.println(vinnerTall);
            } else {
                tmSpin = new Timeline(new KeyFrame(Duration.millis(1), eventRandomSpin));
            }
            tmSpin.setCycleCount(Timeline.INDEFINITE);
            vinnerTallFelt();
            sequentialTransition.getChildren().addAll(tmSpin);
            sequentialTransition.play();
        }
    }

    //Metoder
    private void vinnerTallFelt() {
        //Kalkuler hva som er minst og høyeste rotasjonnivå for å forsikre at hjulet lander riktig
        double vinnerFelt = -feltGrader * (double)vinnerTall;
        vinnerFeltMin = vinnerFelt - 0.2;
        vinnerFeltMax = vinnerFelt + 0.2;
    }

    protected void tilbakeStill() {
        this.setRotate(0);
    }

    //Get-Metoder
    protected boolean getAktivSpin() {return aktivSpin;}
}
