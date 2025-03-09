package com.maxiflexy.minibankapp.controller;

import com.maxiflexy.minibankapp.model.BankAccount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AccountCreationController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField initialDepositField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void createAccount(ActionEvent event) {
        try {
            // Clear previous error message
            errorLabel.setVisible(false);

            // Validate name
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showError("Account holder name cannot be empty!");
                return;
            }

            // Validate initial deposit
            String depositText = initialDepositField.getText().trim();
            if (depositText.isEmpty()) {
                showError("Initial deposit amount cannot be empty!");
                return;
            }

            double initialDeposit;
            try {
                initialDeposit = Double.parseDouble(depositText);
            } catch (NumberFormatException e) {
                showError("Initial deposit must be a valid number!");
                return;
            }

            if (initialDeposit <= 0) {
                showError("Initial deposit must be greater than zero!");
                return;
            }

            // Create bank account
            BankAccount account = new BankAccount(name, initialDeposit);

            // Navigate to the banking operations screen
            navigateToBankingOperations(account);

        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void navigateToBankingOperations(BankAccount account) {
        try {
            // Load the banking operations FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maxiflexy/minibankapp/banking_operations.fxml"));
            Parent root = loader.load();

            // Get the controller and initialize it with the account
            BankingOperationsController controller = loader.getController();
            controller.initData(account);

            // Get the current stage
            Stage stage = (Stage) createAccountButton.getScene().getWindow();

            // Create and set the new scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/maxiflexy/minibankapp/banking-style.css")).toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showError("Error loading banking operations: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
