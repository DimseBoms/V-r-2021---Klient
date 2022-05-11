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
    private TextField innsats;
    private int kronerSatset;
    private StackPane stackPane;
    private Label label;
    private final int BREDDE = 60;
    private static int samletInnsats = 0;
    static double total;

    /**
     * Konstruktøren instansierer et rekkepanel for lottorekker
     * Kuler i panelet er av klasse TallTrekkVisning
     */
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
        this.lyttPåInnsats();
        this.lyttPåSlettRekkePanel();
        this.rammeId++;
        this.rammer.add(this);
    }

    /**
     * Summerer total innsats fra rekker
     * @return
     */
    public double hentInnsats() {
        for (RekkePanelVisning r : rammer) {
            if (!innsats.getText().equals(""))
                total += r.getInnsats();
        }
        System.out.println(total);
        return total;
    }

    /**
     * For hente ut innsats fra tekstfelt
     * @return
     */
    public double getInnsats() {
        if (!innsats.getText().isEmpty()) {
            return Double.parseDouble(innsats.getText());
        } else return 0;
    }

    protected static double aggregerInnsats() {
        return total;

    }

    /**
     * Leser av innsats
     */
    private void lyttPåInnsats() {
        this.innsats.setOnAction(e-> {
            this.kronerSatset = Integer.parseInt(innsats.getText());
            System.out.println("innsats avlest: " + this.kronerSatset);
            double sum = 0;
            for (RekkePanelVisning rad : rammer) {
                sum += rad.getInnsats();
            }
            KontrollerGUI.oppdaterSum(1, sum);
        });
    }

    /**
     * Sletter rader/rekker ved aktivering av slettknapp
     */
    private void lyttPåSlettRekkePanel() {
        this.slettRekkePanel.setOnAction(e -> {
            if (!KontrollerGUI.lykkeHjul.spillStarted) {
                this.rammer.remove(this);
                KontrollerGUI.OppdaterRekkeVisning(this);
                System.out.println("slettKnapp avlest");
            }
        });
    }

    /**
     * Metode for å bygge opp et rekkekomponent fra fil
     * Denne utgaven er ikke redigerbar, kun insatts kan legges inn
     * @param rekkerFraFil
     * @return
     */
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

    /**
     * Legger kuler i array
     * @param tallBall
     */
    void leggTilResponsivBall(TallTrekkVisning tallBall) {
        tallBallArray.add(tallBall);
        this.getChildren().add(tallBall);
    }

    /**
     * Henter tallBallArray
     * @return
     */
    public ArrayList<TallTrekkVisning> getTallBallArray() {
        return tallBallArray;
    }
}

