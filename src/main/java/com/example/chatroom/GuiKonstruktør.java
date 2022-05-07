package com.example.chatroom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GuiKonstruktør {
    private static Thread connTråd;
    public static Font fntStandard = new Font("Verdana",15);
    public static VBox messageField = new VBox();
    private static Tilkobling tilkobling = new Tilkobling();

    protected static void nyMelding(String tekst, String brukerNavn) {
        tilkobling.sendMelding(tekst, brukerNavn);
        Melding melding = new Melding(tekst, brukerNavn);
        VBox boxMelding = new VBox();
        boxMelding.setStyle("-fx-padding: 1 1 1 1;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1 1 1 1;" + "-fx-border-insets: 2;"
                + "-fx-border-radius: 3;" + "-fx-border-color: gray;");
        Label lblBrukerNavnTid = new Label(melding.getBrukerNavn() + " - " + melding.getDate());
        Text txtTekst = new Text(melding.getTekst());
        boxMelding.getChildren().addAll(lblBrukerNavnTid, txtTekst);
        messageField.getChildren().add(boxMelding);
        txtTekst.setWrappingWidth(messageField.getWidth());
    }

    // Metoder for å koble til socket.
    public static void connect() {
        connTråd = new Thread();
    }

    //Konstruktør alle komponetene
    public static HBox makeTop(){
        HBox top = new HBox(10);
        StackPane stack = new StackPane();
        Label lblAppNavn = new Label("HOT CHICKS");
        lblAppNavn.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        Label lblBrukerNavn = new Label("Du er innlogget som: " + Main.getBrukerNavn());
        lblBrukerNavn.setAlignment(Pos.BOTTOM_CENTER);
        lblBrukerNavn.setFont(fntStandard);
        top.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        top.setPadding(new Insets(20.0f));
        top.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0 0 2 0;" + "-fx-border-insets: 0;"
                + "-fx-border-radius: 0;" + "-fx-border-color: black;");
        stack.getChildren().addAll(lblBrukerNavn);
        stack.setAlignment(Pos.BOTTOM_RIGHT);
        StackPane.setMargin(lblBrukerNavn, new Insets(0, 10, 0, 0));
        HBox.setHgrow(stack, Priority.ALWAYS);
        top.getChildren().addAll(lblAppNavn, stack);

        return top;
    }

    public static VBox makeRight(){
        ObservableList<String> names = FXCollections.observableArrayList("Test");
        VBox right = new VBox();
        Button addRoom = new Button("Legg til Rom");
        ListView<String> listView = new ListView<String>(names);
        right.getChildren().addAll(new Label("Rom"), listView);

        return right;
    }

    public static VBox makeLeft(){
        VBox left = new VBox();
        left.setStyle("-fx-padding: 0;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0 1 1 0;" + "-fx-border-insets: 0;"
                + "-fx-border-radius: 0;" + "-fx-border-color: black;");
        ScrollPane sp = new ScrollPane();
        Label lblDeltagere = new Label("Deltagere");
        ArrayList<HBox> kontaktListe = new ArrayList<>();
        String[] kontakt = {"Daniel", "Gaute", "Leo", "Dimitriy", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg", "Bore Troberg"};
        VBox liste = new VBox();

        for(String k : kontakt){
            Label templbl = new Label(k);
            templbl.setFont(fntStandard);
            HBox temp = new HBox();
            temp.setStyle("-fx-padding: 10 0 10 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: black;");
            temp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            temp.getChildren().add(templbl);
            //temp.getChildren().add(new Label(k));
            kontaktListe.add(temp);
        }

        lblDeltagere.setStyle("-fx-padding: 10 10 10 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 0 0 2 0;" + "-fx-border-color: black;");
        lblDeltagere.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        liste.getChildren().addAll(kontaktListe);
        sp.setContent(liste);
        left.getChildren().addAll(lblDeltagere, sp);

        return left;
    }

    public static VBox makeCenter() {
        VBox center = new VBox(10);
        HBox bottom = new HBox(10);
        ScrollPane sp = new ScrollPane();
        sp.setStyle("-fx-background-color:transparent;");
        sp.fitToHeightProperty().set(true);
        sp.fitToWidthProperty().set(true);
        sp.setContent(messageField);
        TextArea writeMessage = new TextArea();
        writeMessage.setWrapText(true);
        bottom.setHgrow(writeMessage, Priority.ALWAYS);
        writeMessage.setPrefColumnCount((int)(Main.WIDTH/7));
        Button sendButton = new Button("Send");
        sendButton.setOnAction( e -> {
            nyMelding(writeMessage.getText(), Main.getBrukerNavn());
            messageField.heightProperty().addListener(observable -> sp.setVvalue(1.0));
        } );
        bottom.setAlignment(Pos.CENTER_RIGHT);
        center.setAlignment(Pos.BOTTOM_CENTER);
        center.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1 1 1 1;" + "-fx-border-insets: 0;"
                + "-fx-border-radius: 0;" + "-fx-border-color: black;");
        bottom.getChildren().addAll(writeMessage, sendButton);
        center.getChildren().addAll(sp, bottom);
        return center;
    }
}


