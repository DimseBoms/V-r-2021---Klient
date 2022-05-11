package com.example.v22klient;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;

public class TallTrekkVisning extends HBox {
    private Circle sirkel;
    private Text tallNode;
    private final int RADIUS = 13;
    private StackPane stackPane;
    private int rekkeNr = 0;
    private boolean tilSletting;
    protected static ArrayList<TallTrekkVisning> rekkeVisninger = new ArrayList<>();


    public TallTrekkVisning(int tall) {
        this.rekkeNr++;
        this.stackPane = new StackPane();
        this.sirkel = new Circle(RADIUS);
        this.sirkel.setStroke(Color.WHITESMOKE);
        this.sirkel.setFill(Color.LIGHTBLUE);
        this.tallNode = new Text(String.valueOf(tall));
        this.tallNode.setBoundsType(TextBoundsType.VISUAL);
        this.stackPane.getChildren().addAll(sirkel, tallNode);
        this.getChildren().add(stackPane);
        this.setOnMouseClicked(e -> {
            this.sirkel.setFill(Color.TOMATO);
            this.tilSletting = true;
        });
        this.rekkeVisninger.add(this);
    }

    public TallTrekkVisning(ArrayList<Integer> rekke) {
        for (Integer i : rekke) {
            this.stackPane = new StackPane();
            this.sirkel = new Circle(RADIUS);
            this.sirkel.setStroke(Color.WHITESMOKE);
            this.sirkel.setFill(Color.BLUE);
            this.tallNode = new Text(i.toString());
            this.tallNode.setBoundsType(TextBoundsType.VISUAL);
            this.stackPane.getChildren().addAll(sirkel, tallNode);
            this.getChildren().add(stackPane);
        }

    }


    public static ArrayList<TallTrekkVisning> getRekkeVisninger() {
        return rekkeVisninger;
    }

    public boolean isTilSletting() {return tilSletting;}

    /*
    private static void slettMarkerteTall() {
        for (TallTrekkVisning rekkeVisning : TallTrekkVisning.getRekkeVisninger()) {
            if (rekkeVisning.isTilSletting()) {
                rekkePanel.getChildren().remove(rekkeVisning);
                antallTrukket--;
            }
        }
    }

     */
}


