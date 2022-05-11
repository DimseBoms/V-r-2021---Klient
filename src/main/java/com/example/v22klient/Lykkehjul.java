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
import javafx.scene.text.*;
import javafx.util.Duration;
import java.util.Random;

/**
 * Lykkehjulet er et Pane som blir brukt i applikasjonen. Den har mange individuelle deler som må kombineres
 * Konstrøkteren inneholder det som trengs for å lage lykkehjulet. Den skalerer med bredden til skjermen
 * Det er mulig å redigere farger og størrelser
 * Antallfelt dynamisk oppdatere basert på hva som er i KontrollerGUI.
 * Objekter og variabler som blir brukt i flere områder deklareres. Blir mer forklart hvordan de blir brukt over metodene
 * Det inneholder bare et lykkehjul i klienten
 *
 */
public class Lykkehjul extends Pane {
    private static double setStørrelse = 5;
    private static final double WIDTH = KontrollerGUI.WIDTH/2 - setStørrelse;
    private static final Color FARGE1 = Color.LIGHTYELLOW;
    private static final Color FARGE2 = Color.CORAL;
    private static final double radius = WIDTH / 2;
    private static double rotasjonSpeed;
    private static double feltGrader, vinnerFeltMin, vinnerFeltMax;
    private static boolean minSpeed, aktivSpin;
    private static int vinnerTall;
    private static int feltAntall;
    private static int vinnerCounter = 0;
    protected static boolean spillStarted;
    protected  static boolean spillAvsluttet;

    private static SequentialTransition sequentialTransition;

    /**
     * @param feltAntall
     * feltAntall er hentet når panelet blir hentet
     * Den er veldig viktig for å kalkulere hvordan gradene blir i lykkehjulet
     * Det er brukt en kombinasjon av arcer og txt til å konstruktrere hjulet
     *
     */
    protected Lykkehjul(int feltAntall) {
        spillAvsluttet = false;
        //For å sjekke om hjulet er i spill
        this.feltAntall = feltAntall;

        //Når lykkehjulet er laget er standaren at den ikke aktivt spinner
        aktivSpin = false;

        //Det er lykkehjulet som inneholder informasjonen om spillet har blitt startet
        spillStarted = false;

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

            //Plasserer 0 før alle siffer
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
                arcFelt.setFill(FARGE1);
            }
            else {arcFelt.setFill(FARGE2);}

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

    /**
     * Animasjon for å spinne hjulet
     * Vinnertallet er allerede trukket før hjulet starter å spinne
     */
    EventHandler<ActionEvent> eventSpin = e -> {
        //Kalkulerer hvor mye fart hjulet har mistet
        double friksjonRotasjonSpeed;
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
            //TODO Sette farge på kuler som matcher
            if (vinnerCounter < 7) {
                KomponenterGUI.updateLykkeHjulButton(1);
            } else {
                KomponenterGUI.visGevinst();
                KomponenterGUI.updateLykkeHjulButton(4);
            }

            aktivSpin = false;
            sequentialTransition.stop();
        }
    };

    /**
     *Denne eventen gjør at vinnertallet blir funnet etter spinningen
     *Har en fart og sakner ned til den lander på noe
     * Den kan bli startet i menyen og er kalt et "random generator"
     **/
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

    /**
     *
     * @param spinType
     * Hvis at spinType true, spin normal. False betyr tilfedlig nummer
     * Før den gjør noe, sjekker den om at den aktivt spinner. Det er for å sikre at hjulet ikke
     * kan spille flere ganger samtidig
     */
    protected void spin(boolean spinType) {
        //Passer på at hjulet ikke aktiverers flere ganger
        if (!aktivSpin) {
            sequentialTransition = new SequentialTransition();
            aktivSpin = true;
            minSpeed = false;

            //Rotasjonskraften i begynnelsen av spin
            rotasjonSpeed = new Random().nextInt(10, 20);

            //Finner vinnertall
            Timeline tmSpin;
            if (spinType) {
                tmSpin = new Timeline(new KeyFrame(Duration.millis(1), eventSpin));
                vinnerTallFelt();
                vinnerCounter++;
            } else {
                tmSpin = new Timeline(new KeyFrame(Duration.millis(1), eventRandomSpin));
            }
            tmSpin.setCycleCount(Timeline.INDEFINITE);
            sequentialTransition.getChildren().addAll(tmSpin);
            sequentialTransition.play();
        } else {System.out.println("Hjulet spinner allerede");}
    }

    //Metoder

    /**
     *Metoden henter et tall fra vinnerrekken og avleser den for hjulet
     */
    private void vinnerTallFelt() {
        //Henter vinnerraden fra server. vinnerCounter starter med 0 og går til 6
        vinnerTall = Tilkobling.svarListe.get(vinnerCounter);
        //Kalkuler hva som er minst og høyeste rotasjonnivå for å forsikre at hjulet lander riktig
        double vinnerFelt = -feltGrader * (double)vinnerTall;
        vinnerFeltMin = vinnerFelt - 0.2;
        vinnerFeltMax = vinnerFelt + 0.2;
        System.out.println(vinnerTall);
    }

    /**
     * Tilbakestiller hjul
     */
    protected void tilbakeStillHjul() {
        this.setRotate(0);
    }

    /**
     * Tilbakestiller vinnerCounter
     */
    protected void tilbakeStillVinnerCounter() {
        vinnerCounter = 0;
    }

    //Get-Metoder
    protected boolean getAktivSpin() {return aktivSpin;}

    protected int getVinnerCounter() {return vinnerCounter;}
}
