package com.example.v22klient;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Lykkehjul extends Pane {

    //Størrselsen på hjulet blir bestemt av bredden av skjermen
    private static final double WIDTH = KontrollerGUI.WIDTH/2;

    //Farge 1 og Farge 2 for feltene
    private static Color farge1 = Color.LIGHTYELLOW;
    private static Color farge2 = Color.CORAL;
    private static final double radius = WIDTH / 2;
    private static double rotasjonSpeed = 10;
    private static double feltGrader, vinnerFeltMin, vinnerFeltMax;
    private static boolean minSpeed, aktivSpin;
    private static int vinnerTall;

    private static SequentialTransition sequentialTransition;

    protected Lykkehjul(int feltAntall) {
        //For å sjekke om hjulet er i spill
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
            Text lblNummer = new Text(nyi);
            Font fontTall = Font.font("Arial", FontWeight.BOLD, WIDTH / 20);
            lblNummer.setFont(fontTall);

            //Kalkulerer hvor tallene skal være i feltet
            //Er også godet for å plassere labels midt i istedenfor nederst til venstre
            widthOffset = lblNummer.getBoundsInLocal().getWidth();
            heightOffset = lblNummer.getBoundsInLocal().getHeight();
            lblNummer.setRotate(feltGraderStart);
            lblNummer.setLayoutX(center + radius * Math.sin(i * (2 * Math.PI / feltAntall)));
            lblNummer.setLayoutY(center - radius * Math.cos(i * (2 * Math.PI / feltAntall)));
            lblNummer.setFill(Color.BLACK);

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
            g.getChildren().add(lblNummer);
        }
        g.setLayoutX(g.getLayoutX()-widthOffset/2);
        g.setLayoutY(g.getLayoutY()+heightOffset/2);
        getChildren().add(g);
        Circle midtSirklel = new Circle(center, center, radius /11, Color.GOLDENROD);
        midtSirklel.setStroke(Color.GAINSBORO);
        getChildren().add(midtSirklel);
    }

    //Animasjon for å spinne hjulet
    EventHandler<ActionEvent> eventSpin = e -> {
        //Kalkulerer hvor mye fart hjulet har mistet
        double friksjonRotasjonSpeed = 0.001 * rotasjonSpeed;

        //Kalkulerer hvor langt fra vinnerfeltet hjulet er
        double distanseFraVFelt = this.getRotate() - vinnerFeltMin;

        //Hvis at hjulet har truffet minimumspeed og distananse fra feltet er under 50, senk farten veldig
        //else if minimumspeed er truffet, ha medium rask fart
        //else farten er basert på friksjon og nåværende fart
        if (minSpeed && distanseFraVFelt < 50 && distanseFraVFelt > 0) {
            this.setRotate(this.getRotate() - 0.02);
        } else if (minSpeed) {
            this.setRotate(this.getRotate() - 0.05);
        } else {
            rotasjonSpeed = rotasjonSpeed - friksjonRotasjonSpeed;
            this.setRotate(this.getRotate() - rotasjonSpeed);
        }

        //Hvis at fart er mindre enn 0.07, mimimum fart er true
        if (rotasjonSpeed < 0.07 && !minSpeed) {
            minSpeed = true;
        }

        //Resett rotasjonen til innenfor 360 grader
        if (this.getRotate() < -360)
            this.setRotate(0);

        //Hvis at hjulet er over riktig felt og minimumfart er truffet, avslutt rotasjonen
        if (this.getRotate() > vinnerFeltMin && this.getRotate() < vinnerFeltMax && minSpeed) {
            aktivSpin = false;
            sequentialTransition.stop();
        }
    };

    protected void spin() {
        sequentialTransition = new SequentialTransition();
        aktivSpin = true;
        minSpeed = false;

        //Rotasjonskraften i begynnelsen av spin
        rotasjonSpeed = 10;

        //Finner vinnertall
        vinnerTall();

        Timeline tmSpin = new Timeline(new KeyFrame(Duration.millis(1), eventSpin));
        tmSpin.setCycleCount(Timeline.INDEFINITE);

        sequentialTransition.getChildren().addAll(tmSpin);
        sequentialTransition.play();
    }

    //Metoder
    private void vinnerTall() {
        vinnerTall = new Random().nextInt(34);
        System.out.println(vinnerTall);

        //Kalkuler hva som er minst og høyeste rotasjonnivå for å forsikre at hjulet lander riktig
        double vinnerFelt = -feltGrader * (double)vinnerTall;
        vinnerFeltMin = vinnerFelt - 0.1;
        vinnerFeltMax = vinnerFelt + 0.1;
    }

    //Get-Metoder
    protected boolean getAktivSpin() {return aktivSpin;}
}
