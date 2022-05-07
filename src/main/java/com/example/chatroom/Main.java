package com.example.chatroom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //Varibler
    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;

    private static String brukernavn = "NULL";

    public static String getBrukerNavn() {
        return brukernavn;
    }

    @Override
    public void start(Stage stage) throws IOException {

        GuiKonstruktør.connect();
        brukernavn = "Gaute";
                //JOptionPane.showInputDialog(null,
                //"Hva er navnet ditt?");
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(GuiKonstruktør.makeTop());
        borderPane.setLeft(GuiKonstruktør.makeLeft());
        borderPane.setCenter(GuiKonstruktør.makeCenter());
        borderPane.setRight(GuiKonstruktør.makeRight());

        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String getBrukernavn() {
        return brukernavn;
    }

}