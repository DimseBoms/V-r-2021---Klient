package com.example.chatroom;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;
    @Override
    public void start(Stage stage) throws IOException {
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
}
