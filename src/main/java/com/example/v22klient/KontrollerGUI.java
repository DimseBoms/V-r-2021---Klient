package com.example.v22klient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KontrollerGUI extends Application {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    public static VBox root = new VBox();
    public static int feltAntall = 34;
    public static Lykkehjul lykkeHjul = new Lykkehjul(feltAntall);
    FlowPane velgTallPane;
    VBox rekkePane;
    static Tilkobling tilkobling;

    // Oppretter tilkobling til tjener
    public static void kobleTilTjener() {
        try {
            tilkobling = new Tilkobling();
        } catch (IOException e) {
            // TODO:
            System.out.println("Klarte ikke koble til tjener\nMå lage feilhåndtering for feil ved tilkobling her");
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Lottorekke> valgteRekker;

    @Override
    public void start(Stage stage) throws IOException {
        root.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(KomponenterGUI.lagInnloggingPane());

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loggInn(String fornavn, String etternavn, String tlf, String epost){
        if(kontrollerTlfNr(tlf) && kontrollerEpost(epost) && kontrollerNavn(fornavn) && kontrollerNavn(etternavn)) {
            // Kobler til tjener
            if (tilkobling == null) kobleTilTjener();
            // Oppretter statisk brukerobjekt på Tilkobling
            tilkobling.loggInnBruker(new Bruker(fornavn, etternavn, epost, tlf));
            // Sjekker om bruker ble suksessfullt logget inn
            if (tilkobling.brukerLoggetInn()) {
                // Viser hovedvindu
                root.getChildren().clear();
                Button btnSpin = new Button("Spinn");
                root.getChildren().addAll(KomponenterGUI.lagLykkeHjulPane(lykkeHjul), KomponenterGUI.lagVelgTallPane(feltAntall), btnSpin);
                btnSpin.setOnAction( e -> {
                    lykkeHjul.spin();
                } );
                // Viser velkomstmelding
                Alert utilgjengeligBrukerAlert = new Alert(Alert.AlertType.CONFIRMATION);
                utilgjengeligBrukerAlert.setHeaderText("Velkommen " + tilkobling.getBruker().getFornavn());
                utilgjengeligBrukerAlert.showAndWait();
            } else {
                // Viser feilmelding ved innlogging
                Alert utilgjengeligBrukerAlert = new Alert(Alert.AlertType.ERROR);
                utilgjengeligBrukerAlert.setHeaderText("Manglende samsvar mellom epost og telefon");
                utilgjengeligBrukerAlert.showAndWait();
            }
        } else {
            // Viser feilmelding angående formattering
            Alert utilgjengeligBrukerAlert = new Alert(Alert.AlertType.INFORMATION);
            utilgjengeligBrukerAlert.setHeaderText("Sjekk formattering på innloggingsinformasjon");
            utilgjengeligBrukerAlert.showAndWait();
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