package com.example.v22klient;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class RekkePanelVisning extends HBox {
    private static int antallRekker = 1;
    protected int rammeId = 1;
    protected static ArrayList<RekkePanelVisning> rammer = new ArrayList<>();
    private Button slettRekkePanel;
    private static TextField innsats;
    private int kronerSatset;
    private StackPane stackPane;
    private Label label;
    private final int BREDDE = 60;
    private static int samletInnsats = 0;
    static double total;

    public RekkePanelVisning() {
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

   //     this.innsats.setOnAction(e-> {
  //          if (Double.parseDouble(innsats.getText()) == 0) {
   //             total += Double.parseDouble(innsats.getText());
 //               System.out.println(total);
 //           }
  //      });
      //  this.innsats.setOnKeyTyped(e-> {
     //       aggregerInnsats();
      //      System.out.println("Aggregerer");
     //   });
        this.getChildren().addAll(stackPane, slettRekkePanel, innsats);
        this.lyttPåSlettRekkePanel();
    //    this.lyttPåInnsats();
        this.rammeId++;
        this.rammer.add(this);
    }

    /**
     * Konstruktør som tar inn en ArrayList av int[]
     * Bygger opp visning av rekker fra fil
     * @param rekkerFraFil
     */
    public RekkePanelVisning(ArrayList<int[]> rekkerFraFil) {
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
        int teller = 0;

        for(int[] liste : rekkerFraFil){
            for(int i = 0; i < 1; i++){
                this.getChildren().add(new TallTrekkVisning(liste[i]));
            }
    //        pane.add(rekkeVisning)
        }

 //       for (int[] tall : rekkerFraFil) {
//            int siffer = tall[teller];
 //           teller++;
 //           this.getChildren().add(new TallTrekkVisning(siffer));
  //      }


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
/*
    protected static double aggregerInnsats() {
        for (RekkePanelVisning r : rammer) {
            total += r.getInnsats();
        }
        System.out.println(total);
        return total;

    }


 */
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

}

