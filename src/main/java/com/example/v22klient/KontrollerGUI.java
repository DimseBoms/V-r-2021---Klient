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

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KontrollerGUI extends Application {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    public static VBox root = new VBox();
    //Ikke lov med oddetall
    public static int feltAntall = 34;
    public static Lykkehjul lykkeHjul = new Lykkehjul(feltAntall);
    FlowPane velgTallPane;
    VBox rekkePane;
    static Tilkobling tilkobling;
    static Bruker bruker;

    // Oppretter tilkobling til tjener
    public static void kobleTilTjener() {
        try {
            tilkobling = new Tilkobling();
        } catch (IOException e) {
            // TODO: Må lage feilhåndtering for feil ved tilkobling her. Gjerne GUI Komponenter
            System.out.println("Klarte ikke koble til tjener");
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
            kobleTilTjener();
            // Oppretter statisk brukerobjekt på Tilkobling
            bruker = new Bruker(fornavn, etternavn, epost, tlf);
            tilkobling.loggInnBruker(bruker);
            // Sjekker om bruker ble suksessfullt logget inn
            if (tilkobling.brukerLoggetInn()) {
                // Viser hovedvindu
                root.getChildren().clear();
                Button btnSpin = new Button("Spinn");
                root.getChildren().addAll(KomponenterGUI.lagLykkeHjulPane(lykkeHjul), KomponenterGUI.lagVelgInputPane());
                //KomponenterGUI.lagVelgTallPane(feltAntall), btnSpin
                btnSpin.setOnAction( e -> {
                    if (!lykkeHjul.getAktivSpin())
                    lykkeHjul.spin();
                } );
                //testSendingAvRekke(bruker);
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

    private static void testSendingAvRekke(Bruker bruker) {
        ArrayList<Integer> rekkeMedTall = new ArrayList<>();
        rekkeMedTall.add(69);
        rekkeMedTall.add(69);
        rekkeMedTall.add(69);
        rekkeMedTall.add(69);
        rekkeMedTall.add(12);
        rekkeMedTall.add(42);
        rekkeMedTall.add(12);
        Rekke rekke= new Rekke(rekkeMedTall, 5, bruker);
        tilkobling.sendRekke(bruker);
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
                // make new rekke
                // add rekke to rekkepane
            }
        }
        System.out.println(KomponenterGUI.rekkeTall);
    }

    public static ArrayList<int[]> lesFil(){
        ArrayList<int[]> rekker = new ArrayList<>();
        File fil = new File(KomponenterGUI.filNavn);
        int antallRekker = 0;

        try {
            Scanner scanner = new Scanner(fil);
            while (scanner.hasNextLine()){
                antallRekker++;
                scanner.nextLine();
            }
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        String[] linjer = new String[antallRekker];

        try{
            Scanner scanner = new Scanner(fil);
            for(int i = 0; i < antallRekker; i++){
                linjer[i] = scanner.nextLine();
                //System.out.println(linjer[i]);
            }

        }catch (Exception e){
            System.out.println("Problemer med fil");
            e.printStackTrace();
        }

        for(int i = 0; i < linjer.length; i++){
            int[] arr = new int[7];

            try {
                String[] linje = linjer[i].split(" ");
                //System.out.println(linjer[i]);
                for (int k = 0; k < 7; k++) {
                    arr[k] = Integer.parseInt(linje[k]);
                }
                rekker.add(arr);
            }catch (Exception e){
                System.out.println("Det er en feil i filen. Sjekk tekstfilen og prøv igjen.");
                varsleBruker("Det er en feil i tekstfilen.");
                rekker.clear();
                break;
            }
        }
        System.out.println(rekker);

        return rekker;
    }

    /**
     * Meldinger til bruker med class Alert
     * @param melding - tar imot og viser String
     */
    private static void varsleBruker(String melding) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText("Melding til bruker:");
        alert.setContentText(melding);
        alert.showAndWait().ifPresent((btnType) -> {});
    }

}