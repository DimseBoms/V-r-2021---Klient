package com.example.v22klient;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class KomponenterGUI {

    public static TextField telefonInput, epostInput, fNavnInput, eNavnInput;
    public static ArrayList<ToggleButton> tallKnapperListe;
    public static ArrayList<Integer> rekkeTall;

    public static VBox lagLykkeHjulPane(Lykkehjul lykkeHjul){
        double nålLengde = KontrollerGUI.WIDTH/30;
        double nålGrader = KontrollerGUI.WIDTH/6;
        Polygon nål = new Polygon(0, 0, (20 * Math.tan(nålGrader)), -nålLengde, -(nålLengde * Math.tan(nålGrader)), -nålLengde);
        nål.setFill(Color.DARKRED);
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

        Label loggInnLbl = new Label("Logg inn");
        Button loggInnBtn = new Button("Logg inn");
        telefonInput = new TextField();
        epostInput = new TextField();
        fNavnInput = new TextField();
        eNavnInput = new TextField();

        HBox navnBoks = new HBox(10);
        navnBoks.getChildren().addAll(new Label("Fornavn: "), fNavnInput, new Label("Etternavn"), eNavnInput);

        HBox telefonBoks = new HBox(10);
        telefonBoks.getChildren().addAll(new Label("Telefon: "), telefonInput);

        HBox epostBoks = new HBox(10);
        epostBoks.getChildren().addAll(new Label("Epost: "), epostInput);

        loggInnBtn.setOnAction( e -> {
            KontrollerGUI.loggInn(fNavnInput.getText(), eNavnInput.getText(), telefonInput.getText(), epostInput.getText());
        });

        VBox loggInnPane = new VBox(20);
        loggInnPane.getChildren().addAll(loggInnLbl, navnBoks, telefonBoks, epostBoks, loggInnBtn);

        return loggInnPane;
    }
    static Polygon createTriangle(Point2D origin, double length, double angle){
        Polygon fovTriangle = new Polygon(
                0d, 0d,
                (length * Math.tan(angle)), -length,
                -(length * Math.tan(angle)), -length
        );
        fovTriangle.setFill(Color.DARKRED);
        fovTriangle.setLayoutX(origin.getX());
        fovTriangle.setLayoutY(origin.getY());
        return fovTriangle;
    }

    //gevinst-visning



    //ingen gevinst-visning


}
