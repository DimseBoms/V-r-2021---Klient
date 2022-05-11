package com.example.v22klient;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

import static com.example.v22klient.KontrollerGUI.lykkeHjul;

/**
 * KomponenterGUI inneholder panes og komponter som blir brukt i applikasjonen
 */
public class KomponenterGUI {

    public static TextField telefonInput, epostInput, fNavnInput, eNavnInput, lastOppFilNavnInput;
    public static ArrayList<ToggleButton> tallKnapperListe;
    public static ArrayList<Integer> rekkeTall;
    protected static int toggleTeller = 0;
    public static Button velgSelv, lastOppFil, lastOpp, velgFil, angre, btnSpin;
    public static String filNavn;
    public static HBox velgInputPane;
    public static VBox lykkeHjulPane;

    /**
     * Metoden lager en pane for å inneholde lykkehjulet + nål og knapp som er tilkoblet til lykkehjulet
     *
     * @param lykkeHjul
     * @return
     * Den returnerer en VBox til KontrollerGUI
     */
    public static VBox lagLykkeHjulPane(Lykkehjul lykkeHjul){
        double nålLengde = KontrollerGUI.WIDTH/30;
        double nålGrader = KontrollerGUI.WIDTH/6;
        Polygon nål = new Polygon(0, 0, (20 * Math.tan(nålGrader)), -nålLengde, -(nålLengde * Math.tan(nålGrader)), -nålLengde);
        nål.setFill(Color.DARKRED);
        nål.setStroke(Color.BLACK);
        nål.setLayoutX(0);
        nål.setLayoutY(0);

        btnSpin = new Button();
        updateLykkeHjulButton(2); //Setter til start spill
        btnSpin.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        btnSpin.setAlignment(Pos.BOTTOM_CENTER);
        btnSpin.setOnAction( e -> {
            if (!lykkeHjul.getAktivSpin()) {
                switch (btnSpin.getText()) {
                    case "$ SPIN $":
                        if (lykkeHjul.getVinnerCounter() < 7) {
                            lykkeHjul.spin(true);
                            updateLykkeHjulButton(3); //Setter button til ...spinning
                        }
                        break;
                    case "START SPILL":
                        KontrollerGUI.tilkobling.sendRekke(Tilkobling.bruker);
                        lykkeHjul.spillStarted = true;
                        updateLykkeHjulButton(1); //Setter til $ SPIN $
                        break;
                    case "START NYTT SPILL":
                        lykkeHjul.spillStarted = false;
                        lykkeHjul.tilbakeStillVinnerCounter();
                        updateLykkeHjulButton(4); //Setter til START SPILL
                        break;
                    default: System.out.println("ERROR"); break;
                }
            }
        });

        lykkeHjulPane = new VBox();
        lykkeHjulPane.getChildren().addAll(nål, lykkeHjul, btnSpin);
        lykkeHjulPane.setAlignment(Pos.BOTTOM_CENTER);
        lykkeHjulPane.setPadding(new Insets(50));
        lykkeHjulPane.setSpacing(30);
        return lykkeHjulPane;
    }

    /**
     * Metoden oppretter en flowpane som holder på togglebuttons
     * Togglebuttons aktiverer bygging av rekker
     * @param antallTall
     * @return
     */
    public static FlowPane lagVelgTallPane(int antallTall){
        FlowPane velgtallPane = new FlowPane();
        velgtallPane.setPadding(new Insets(0,0,10,0));
        velgtallPane.setVgap(5);
        velgtallPane.setHgap(5);
        velgtallPane.setAlignment(Pos.CENTER);
        tallKnapperListe = new ArrayList<>();
        rekkeTall = new ArrayList<>();
        //ToggleGroup group = new ToggleGroup();

        for(int i = 0; i < antallTall; i++){
            ToggleButton velgTallKnapp = new ToggleButton("" + (i + 1));
            velgTallKnapp.setPrefWidth(30);
            velgTallKnapp.setOnAction( e -> {
                if (!KontrollerGUI.lykkeHjul.spillStarted) {
                    velgTallKnapp.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,null,null)));
                    KontrollerGUI.velgTall(antallTall);
                    // TODO: her skal vi sende tallet fra knappen til kula
                    toggleTeller++;
                }
            });
            tallKnapperListe.add(velgTallKnapp);
        }
        for(ToggleButton b : tallKnapperListe){
            velgtallPane.getChildren().add(b);
        }
        return velgtallPane;
    }

    /**
     * Resetter toggle av knapper
     */
    public static void resetToggle() {
        for (ToggleButton tb : tallKnapperListe) {
            tb.setSelected(false);
            tb.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));

        }
    }

    /**Metoden oppretter en VBox som visuelt viser rekkene
     *
     * @return
     */
    public VBox lagRekkePane(){
        VBox rekkePane = new VBox();
        for (int i = 0; i < KontrollerGUI.valgteRekker.size(); i++){
            HBox rekkeBoks = new HBox();
            rekkeBoks.getChildren().add(KontrollerGUI.valgteRekker.get(i));
            rekkePane.getChildren().add(rekkeBoks);
        }

        return rekkePane;
    }

    /**
     *
     * @return
     * Lager VBox som inneholder komponenter for innlogging
     */
    public static VBox lagInnloggingPane(){
        HBox tittelBox = new HBox();
        tittelBox.setAlignment(Pos.CENTER);
        tittelBox.setPadding(new Insets(40, 10, 10, 10));
        Text lblDollarsymbol = new Text("$ ");
        lblDollarsymbol.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        lblDollarsymbol.setFill(Color.YELLOW);
        lblDollarsymbol.setStroke(Color.BLACK);
        Text loggInnLbl = new Text("WebLotto - Et spill for Norge");
        loggInnLbl.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        loggInnLbl.setFill(Color.WHITE);
        tittelBox.getChildren().addAll(lblDollarsymbol, loggInnLbl);

        Button loggInnBtn = new Button("Logg inn");
        telefonInput = new TextField("12345678");
        epostInput = new TextField("test@email.ts");
        fNavnInput = new TextField("ForTest");
        eNavnInput = new TextField("EtterTest");

        VBox mainBoks = new VBox(10);
        mainBoks.setAlignment(Pos.CENTER);
        mainBoks.setMaxWidth(400);
        mainBoks.setPadding(new Insets(20, 20, 20, 20));
        mainBoks.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null))); // Det var en rød strek her. Endret background

        mainBoks.getChildren().addAll(new Label("Fornavn"), fNavnInput, new Label("Etternavn"), eNavnInput);
        mainBoks.getChildren().addAll(new Label("Telefon"), telefonInput);
        mainBoks.getChildren().addAll(new Label("Epost"), epostInput);

        loggInnBtn.setOnAction( e -> {
            KontrollerGUI.loggInn(fNavnInput.getText(), eNavnInput.getText(), telefonInput.getText(), epostInput.getText());
        });

        VBox loggInnPane = new VBox(20);
        loggInnPane.getChildren().addAll(tittelBox, mainBoks, loggInnBtn);
        loggInnPane.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        loggInnPane.setAlignment(Pos.CENTER);

        return loggInnPane;
    }

    /**
     * @return
     * Lager pane for velging av hvordan man ønsker å hente rekker
     */
    public static HBox lagVelgInputPane(){
        velgInputPane = new HBox();
        velgInputPane.setPadding(new Insets(10));
        velgInputPane.setSpacing(10);
        velgInputPane.setAlignment(Pos.CENTER);
        velgSelv = new Button("Velg tall selv");
        velgSelv.setPrefWidth(200);
        velgSelv.setOnAction( event ->{
            KontrollerGUI.visVelgSelv();
        });

        lastOppFil = new Button("Last opp rekker fra fil");
        lastOppFil.setPrefWidth(200);
        lastOppFil.setOnAction( e -> {
            lagLastOppFilPane();
        });
        velgInputPane.getChildren().addAll(velgSelv, lastOppFil);
        return velgInputPane;
    }

    /**
     *Lager pane for opplasting av filer
     */
    public static void lagLastOppFilPane(){
        velgInputPane.getChildren().clear();
        lastOppFilNavnInput = new TextField();
        velgFil = new Button("bla gjennom");
        velgFil.setOnAction( e -> {
            FileChooser fileChooser = new FileChooser();
            filNavn = fileChooser.showOpenDialog(new Stage()).toString();
            lastOppFilNavnInput.setText(filNavn);
        });
        angre = new Button("Gå tilbake");
        angre.setOnAction(e-> {
            velgInputPane.getChildren().clear();
            velgInputPane.getChildren().addAll(lagVelgInputPane());
        });
        lastOpp = new Button("Last opp");
        lastOpp.setOnAction( event -> {
            KontrollerGUI.lesFil(filNavn);
        });
        velgInputPane.getChildren().addAll(velgFil, lastOpp, angre, lastOppFilNavnInput);
    }

    /**
     * @param setBtnSpin
     * Oppdaterer button basert på hvilken int som blir sendt inn. Må være mellom 1-4
     * Hvis at den er 1, vil teksten være SPIN
     * 2 = Start spill
     * 3 = Spinner...
     * 4 = Start nytt spill
     */
    public static void updateLykkeHjulButton(int setBtnSpin) {
        switch (setBtnSpin) {
            case 1:
                btnSpin.setText("$ SPIN $");
                btnSpin.setBackground(Background.fill(Color.GOLDENROD));
                btnSpin.setTextFill(Color.BLACK);
                break;
            case 2:
                btnSpin.setText("START SPILL");
                btnSpin.setBackground(Background.fill(Color.GOLDENROD));
                btnSpin.setTextFill(Color.BLACK);
                break;
            case 3:
                btnSpin.setText("Spinner...");
                btnSpin.setBackground(Background.fill(Color.WHITESMOKE));
                btnSpin.setTextFill(Color.GRAY);
                break;
            case 4:
                btnSpin.setText("START NYTT SPILL");
                btnSpin.setBackground(Background.fill(Color.GOLDENROD));
                btnSpin.setTextFill(Color.BLACK);
            default:
                btnSpin.setText("updateLykkeHjulButton input må være mellom 1-4");
        }
    }


    /**
     * Gevinst-visning
     * Denne lages hvis at bruker vant noe gevinst. Hvis ikke starter den opp andre visningen visIngenGevinst()
     */
    protected static void visGevinst() {
        if (Tilkobling.svarListe.size() > 0) {
            Alert alertVisGevinst = new Alert(Alert.AlertType.CONFIRMATION);
            String vinnerRekke = Tilkobling.svarListe.toString();
            String gevinst = Tilkobling.gevinnstListe.toString();
            alertVisGevinst.setHeaderText("Du vant på følgende rekker: " + vinnerRekke + "\n"
                                         + "Din gevinst er: " + gevinst);
            alertVisGevinst.showAndWait();
        } else {visIngenGevinst();}
    }

    /**
     * Denne alerten starter hvis at bruker ikke vant noe gevinst
     */
    protected static void visIngenGevinst() {
        Alert alertVisIngenGevinst = new Alert(Alert.AlertType.CONFIRMATION);
        alertVisIngenGevinst.setHeaderText("Du vant ingenting, men bedre lykke neste gang!");
        alertVisIngenGevinst.showAndWait();
    }

}
