package com.example.v22klient;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Lykkehjul extends Pane {
    private static final double WIDTH = KontrollerGUI.WIDTH/2;
    private static Color farge1 = Color.LIGHTYELLOW;
    private static Color farge2 = Color.CORAL;
    private static ArrayList<Arc> listFelt;
    double radius = WIDTH / 2;
    private static double rotasjonSpeed = 10;
    private static double feltGrader, vinnerFeltMin, vinnerFeltMax;
    private static boolean minSpeed;
    private static int vinnerTall;
    SequentialTransition sequentialTransition;

    public Lykkehjul(int feltAntall) {
        //Kalkulerer hvor mange grader hvert felt er
        feltGrader = 360/(double)feltAntall;
        double center = WIDTH/2;
        //Starter med
        double feltGraderStart = feltGrader;

        this.setMaxSize(WIDTH, WIDTH);
        this.setMinSize(WIDTH, WIDTH);

        for (int i = 1; feltAntall>=i; i++) {
            //Lager en arc - Et arc er et felt
            Arc arcFelt = new Arc();
            listFelt = new ArrayList<>();

            //Plassere nummer i hjulet
            Label lblNummer = new Label(""+i);
            lblNummer.setFont(Font.font("Arial", FontWeight.BOLD, WIDTH/20));

            //Kalkulerer hvor tallene skal være i feltet
            //Er også godet for å plassere labels midt i istedenfor nederst til venstre
            lblNummer.setLayoutX((center-WIDTH/80)+ radius *Math.sin(i * (2 * Math.PI / feltAntall)));
            lblNummer.setLayoutY((center-WIDTH/80)- radius *Math.cos(i * (2 * Math.PI / feltAntall)));
            lblNummer.setRotate(feltGraderStart);
            lblNummer.setMaxWidth(Double.MAX_VALUE);
            AnchorPane.setLeftAnchor(lblNummer, 0.0);
            AnchorPane.setRightAnchor(lblNummer, 0.0);
            lblNummer.setAlignment(Pos.CENTER);

            //Oppdaterer gradene for neste felt

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
            listFelt.add(arcFelt);
            getChildren().add(arcFelt);
            getChildren().add(lblNummer);
        }
        Circle midtSirklel = new Circle(center, center, radius /11, Color.GOLDENROD);
        midtSirklel.setStroke(Color.GAINSBORO);
        getChildren().add(midtSirklel);
    }

    EventHandler<ActionEvent> eventSpin = e -> {
        double friksjonRotasjonSpeed = -0.001 * -rotasjonSpeed;
        if (rotasjonSpeed > -0.03 && !minSpeed) {
            minSpeed = true;
        }
        if (minSpeed) {
            this.setRotate(this.getRotate() - 0.03);
        } else {
            rotasjonSpeed = rotasjonSpeed - friksjonRotasjonSpeed;
            this.setRotate(this.getRotate() + rotasjonSpeed);
        }
        if (this.getRotate() < -360)
            this.setRotate(0);
        if (this.getRotate() > vinnerFeltMin && this.getRotate() < vinnerFeltMax && minSpeed) {
            sequentialTransition.stop();
        }
    };

    public void spin() {
        sequentialTransition = new SequentialTransition();
        minSpeed = false;
        rotasjonSpeed = -10;
        vinnerTall();

        Timeline tmSpin = new Timeline(
                new KeyFrame(Duration.millis(1), eventSpin));
        tmSpin.setCycleCount(Timeline.INDEFINITE);

        sequentialTransition.getChildren().addAll(tmSpin);
        sequentialTransition.play();
    }

    public void vinnerTall() {
        vinnerTall = new Random().nextInt(34);
        System.out.println(vinnerTall);
        double vinnerFelt = -feltGrader * (double)vinnerTall;
        vinnerFeltMin = vinnerFelt - 0.1;
        vinnerFeltMax = vinnerFelt + 0.1;
    }
}
