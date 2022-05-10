package com.example.v22klient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KontrollerGUI extends Application {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    public static int feltAntall = 34;
    public static VBox root = new VBox();
    public static Lykkehjul lykkeHjul = new Lykkehjul(34);
    FlowPane velgTallPane;
    VBox rekkePane;

    public static ArrayList<Lottorekke> valgteRekker;

    @Override
    public void start(Stage stage) throws IOException {

        root.getChildren().add(KomponenterGUI.lagInnloggingPane());

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loggInn(String fNavn, String eNavn, String tlf, String epost){
        //server godtar innlogging
        boolean check = true;

        if(kontrollerTlfNr(KomponenterGUI.telefonInput.getText()) && kontrollerEpost(KomponenterGUI.epostInput.getText()) &&
                kontrollerNavn(KomponenterGUI.fNavnInput.getText()) && kontrollerNavn(KomponenterGUI.eNavnInput.getText())){
            check = true;
        }

        if(check){
            root.getChildren().clear();
            Button btnSpin = new Button("Spinn");
            root.getChildren().addAll(KomponenterGUI.lagLykkeHjulPane(lykkeHjul), KomponenterGUI.lagVelgTallPane(feltAntall), btnSpin);
            btnSpin.setOnAction( e -> {
                lykkeHjul.spin();
            } );
        }
    }

    /**
     * Kontroll av format på telefonnummer.
     * Bør man fjerne mellomrom? i så fall må disse fjernes fra original String også!!
     * Bør utvides til å akseptere flere formater
     */
    private static boolean kontrollerTlfNr(String tlf) {
        Pattern pattern = Pattern.compile("^\\d{8}$");
        Matcher matcher = pattern.matcher(tlf);
        return matcher.matches();
    }

    private static boolean kontrollerEpost(String epost) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(epost);
        return matcher.matches();
    }

    private static boolean kontrollerNavn(String navn) {
        Pattern pattern = Pattern.compile("^[A-Za-z]\\w{1,60}$");
        Matcher matcher = pattern.matcher(navn);
        return matcher.matches();
    }

    public static void velgTall(int antallTall){
        for(int k = 0; k < antallTall; k++){
            if(KomponenterGUI.tallKnapperListe.get(k).isSelected() && KomponenterGUI.rekkeTall.size() < 7){
                if(!KomponenterGUI.rekkeTall.contains(KomponenterGUI.tallKnapperListe.indexOf(KomponenterGUI.tallKnapperListe.get(k)) + 1)) {
                    KomponenterGUI.rekkeTall.add(KomponenterGUI.tallKnapperListe.indexOf(KomponenterGUI.tallKnapperListe.get(k)) + 1);
                }
            }
            if(KomponenterGUI.rekkeTall.size() == 7){

            }
        }
        System.out.println(KomponenterGUI.rekkeTall);
    }
}