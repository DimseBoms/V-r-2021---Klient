package com.example.v22klient;

import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class KomponenterGUI {

    public static TextField telefonInput, epostInput, fNavnInput, eNavnInput;
    public static ArrayList<ToggleButton> tallKnapperListe;
    public static ArrayList<Integer> rekkeTall;

    public static Pane lagLykkeHjulPane(){
        Pane lykkeHjulPane = new Pane();
        Circle lykkehjul = new Circle(KontrollerGUI.WIDTH / 2, KontrollerGUI.HEIGHT / 8, 100);
        lykkeHjulPane.getChildren().add(lykkehjul);

        return lykkeHjulPane;
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

    //gevinst-visning



    //ingen gevinst-visning


}
