package com.maxiflexy.minibankapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class BankingAppFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the initial FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maxiflexy/minibankapp/view/account_creation.fxml"));
        Parent root = loader.load();

        // Setup the scene
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/maxiflexy/minibankapp/css/banking-style.css")).toExternalForm());

        // Configure the stage
        primaryStage.setTitle("Dream-Devs Banking Application");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
