package com.example.chatroom;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    //Varibler
    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;

    private static String brukerNavn = "NULL";

    @Override
    public void start(Stage stage) throws IOException {
        brukerNavn = "Gaute";
                //JOptionPane.showInputDialog(null,
                //"Hva er navnet ditt?");
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(GuiKonstruktør.makeTop());
        borderPane.setLeft(GuiKonstruktør.makeLeft());
        borderPane.setCenter(GuiKonstruktør.makeCenter());
        borderPane.setRight(GuiKonstruktør.makeRight());
        GuiKonstruktør.connect();

        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String getBrukerNavn() {
        return brukerNavn;
    }

}