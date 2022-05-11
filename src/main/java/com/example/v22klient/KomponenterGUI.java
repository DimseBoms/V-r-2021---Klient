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

    public static TextField telefonInput, epostInput, fNavnInput, eNavnInput;
    public static ArrayList<ToggleButton> tallKnapperListe;
    public static ArrayList<Integer> rekkeTall;
    protected static int toggleTeller = 0;
    public static Button velgSelv, lastOppFil, lastOpp;
    public static String filNavn;
    public static VBox velgInputPane;

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
        tallKnapperListe = new ArrayList<>();
        rekkeTall = new ArrayList<>();
        //ToggleGroup group = new ToggleGroup();

        for(int i = 0; i < antallTall; i++){
            ToggleButton velgTallKnapp = new ToggleButton("" + (i + 1));
            velgTallKnapp.setOnAction( e -> {
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

    public static VBox lagVelgInputPane(){
        velgInputPane = new VBox();
        velgSelv = new Button("Velg tall selv");
        velgSelv.setOnAction( event ->{
            System.out.println("Vis tallknapper osv");
            KontrollerGUI.root.getChildren().remove(velgInputPane);
            KontrollerGUI.root.getChildren().add(lagVelgTallPane(34));
        });

        lastOppFil = new Button("Last opp rekker fra fil");
        lastOppFil.setOnAction( event -> {
            lagLastOppFilPane();
        });

        velgInputPane.getChildren().addAll(velgSelv, lastOppFil);
        return velgInputPane;
    }

    public static void lagLastOppFilPane(){
        lastOpp = new Button("Last opp");
        lastOpp.setOnAction( event -> {
            KontrollerGUI.lesFil();
            velgInputPane.getChildren().clear();
        });

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Velg filen du vil laste opp");

        File file = fileChooser.showOpenDialog(new Stage());


        if(file != null) {
            velgInputPane.getChildren().clear();
            filNavn = file.toString();
            Label lbl = new Label(filNavn);
            lbl.setTextFill(Color.WHITE);
            Button lastOppNy = new Button("Velg ny fil");
            lastOppNy.setOnAction( event -> {
                velgInputPane.getChildren().clear();
                lagLastOppFilPane();
            });
            velgInputPane.getChildren().addAll(lbl, lastOpp, lastOppNy);
        }

    }


    //gevinst-visning



    //ingen gevinst-visning


}
