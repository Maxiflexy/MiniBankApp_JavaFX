package com.maxiflexy.minibankapp;

import com.maxiflexy.minibankapp.model.BankAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

import java.util.Objects;

public class BankingAppFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the initial FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maxiflexy/minibankapp/account_creation.fxml"));
        Parent root = loader.load();

        // Setup the scene
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/maxiflexy/minibankapp/banking-style.css")).toExternalForm());

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
