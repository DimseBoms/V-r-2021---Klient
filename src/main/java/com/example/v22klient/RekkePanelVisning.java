package com.example.v22klient;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class RekkePanelVisning extends HBox {
    private static int antallRekker = 1;
    protected int rammeId = 1;
    protected static ArrayList<RekkePanelVisning> rammer = new ArrayList<>();
    private Button slettRekkePanel;
    private TextField innsats;
    private int kronerSatset;
    private StackPane stackPane;
    private Label label;
    private final int BREDDE = 60;


    public RekkePanelVisning() {
        this.setSpacing(15);
        this.label = new Label();
        label.setText("Rekke " + antallRekker++); // 1 byttes ut med klassevariabel som itereres med ++
        this.stackPane = new StackPane(this.label);
        this.stackPane.setAlignment(Pos.BOTTOM_CENTER);
        this.slettRekkePanel = new Button("Slett");
        this.slettRekkePanel.setPrefWidth(BREDDE);
        this.innsats = new TextField();
        this.innsats.setPrefWidth(BREDDE);
        this.innsats.setPromptText("Innsats");
        this.getChildren().addAll(label, slettRekkePanel, innsats);
        this.lyttPåSlettRekkePanel();
        this.lyttPåInnsats();
        this.rammeId++;
        this.rammer.add(this);

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

}

