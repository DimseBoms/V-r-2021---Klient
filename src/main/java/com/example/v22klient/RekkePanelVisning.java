package com.example.v22klient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class RekkePanelVisning extends HBox {
    private static int antallRekker = 1;
    protected int rammeId = 1;
    protected static ArrayList<RekkePanelVisning> rammer = new ArrayList<>();
    ArrayList<TallTrekkVisning> tallBallArray;
    private Button slettRekkePanel;
    private static TextField innsats;
    private int kronerSatset;
    private StackPane stackPane;
    private Label label;
    private final int BREDDE = 60;
    private static int samletInnsats = 0;
    static double total;

    public RekkePanelVisning() {
        tallBallArray = new ArrayList<>();
        this.setSpacing(15);
        this.label = new Label();
        this.label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        this.label.setTextFill(Color.WHITE);
        this.label.setText("Rekke " + antallRekker++);
        this.label.setTextAlignment(TextAlignment.CENTER);
        this.stackPane = new StackPane(this.label);
        this.slettRekkePanel = new Button("Slett");
        this.slettRekkePanel.setPrefWidth(BREDDE);
        this.innsats = new TextField();
        this.innsats.setPrefWidth(BREDDE);
        this.innsats.setPromptText("Innsats");
        this.getChildren().addAll(stackPane, slettRekkePanel, innsats);
        this.lyttPåSlettRekkePanel();
        this.rammeId++;
        this.rammer.add(this);
    }

    public static double hentInnsats() {
        for (RekkePanelVisning r : rammer) {
            if (!innsats.getText().equals(""))
                total += r.getInnsats();
        }
        System.out.println(total);
        return total;
    }

    public double getInnsats() {
        return Double.parseDouble(innsats.getText());
    }

    protected static double aggregerInnsats() {
        return total;

    }

    private void lyttPåInnsats() {
        this.innsats.setOnAction(e-> {
            this.kronerSatset = Integer.parseInt(innsats.getText());
            System.out.println("innsats avlest: " + this.kronerSatset);
        });
    }

    private void lyttPåSlettRekkePanel() {
        this.slettRekkePanel.setOnAction(e -> {
            this.rammer.remove(this);
            KontrollerGUI.OppdaterRekkeVisning(this);
            System.out.println("slettKnapp avlest");
        });
    }

    protected static VBox visRekkerFraFil(ArrayList<ArrayList<Integer>> rekkerFraFil) {
        VBox boks = new VBox();
        boks.setPadding(new Insets(10));
        boks.setSpacing(5);
        boks.setStyle("-fx-background-color: dimgrey");
        for(ArrayList<Integer> liste : rekkerFraFil){
            RekkePanelVisning rekke = new RekkePanelVisning();
            for (int k = 0; k < 7; k++){
                rekke.getChildren().add(new TallTrekkVisning(liste.get(k)));
            }
            boks.getChildren().add(rekke);
        }
        return boks;
    }

    void leggTilResponsivBall(TallTrekkVisning tallBall) {
        tallBallArray.add(tallBall);
        this.getChildren().add(tallBall);
    }

    public ArrayList<TallTrekkVisning> getTallBallArray() {
        return tallBallArray;
    }
}

