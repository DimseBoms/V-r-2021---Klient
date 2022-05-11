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

public class KomponenterGUI {

    public static TextField telefonInput, epostInput, fNavnInput, eNavnInput, lastOppFilNavnInput;
    public static ArrayList<ToggleButton> tallKnapperListe;
    public static ArrayList<Integer> rekkeTall;
    protected static int toggleTeller = 0;
    public static Button velgSelv, lastOppFil, lastOpp, velgFil, angre;
    public static String filNavn;
    public static HBox velgInputPane;

    public static VBox lagLykkeHjulPane(Lykkehjul lykkeHjul){
        double nålLengde = KontrollerGUI.WIDTH/30;
        double nålGrader = KontrollerGUI.WIDTH/6;
        Polygon nål = new Polygon(0, 0, (20 * Math.tan(nålGrader)), -nålLengde, -(nålLengde * Math.tan(nålGrader)), -nålLengde);
        nål.setFill(Color.DARKRED);
        nål.setStroke(Color.BLACK);
        nål.setLayoutX(0);
        nål.setLayoutY(0);
        VBox stack = new VBox();
        stack.getChildren().addAll(nål, lykkeHjul);
        stack.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        stack.setAlignment(Pos.CENTER);
        stack.setPadding(new Insets(50));
        stack.setSpacing(25);
        return stack;
    }

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
                velgTallKnapp.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,null,null)));
                KontrollerGUI.velgTall(antallTall);
                // TODO: her skal vi sende tallet fra knappen til kula
                toggleTeller++;
            });
            tallKnapperListe.add(velgTallKnapp);
        }
        for(ToggleButton b : tallKnapperListe){
            velgtallPane.getChildren().add(b);
        }
        return velgtallPane;
    }

    public static void resetToggle() {
        for (ToggleButton tb : tallKnapperListe) {
            tb.setSelected(false);
            tb.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));

        }
    }


    public VBox lagRekkePane(){
        VBox rekkePane = new VBox();
        for (int i = 0; i < KontrollerGUI.valgteRekker.size(); i++){
            HBox rekkeBoks = new HBox();
            rekkeBoks.getChildren().add(KontrollerGUI.valgteRekker.get(i));
            rekkePane.getChildren().add(rekkeBoks);
        }

        return rekkePane;
    }

    //Innlogging
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
     * GIR NULLPOINTER DERSOM BRUKER AVBRYTER. BØR HA BEDRE UNNTAKSHÅNDTERING
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


    //gevinst-visning



    //ingen gevinst-visning


}
