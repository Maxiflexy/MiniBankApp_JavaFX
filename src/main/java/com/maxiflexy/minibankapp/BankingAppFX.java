package com.maxiflexy.minibankapp;

import com.maxiflexy.minibankapp.model.BankAccount;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

import java.util.Objects;

public class BankingAppFX extends Application {

    private BankAccount account;
    private TextField nameField;
    private TextField initialDepositField;
    private TextField amountField;
    private TextArea transactionHistoryArea;
    private Label balanceLabel;
    private VBox mainLayout;
    private VBox accountCreationLayout;
    private VBox bankingOperationsLayout;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dream-Devs Banking Application");

        // Create account creation layout
        createAccountCreationLayout();

        // Create banking operations layout (initially hidden)
        createBankingOperationsLayout();

        // Main layout will initially show account creation
        mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().add(accountCreationLayout);

        // Create scene
        Scene scene = new Scene(mainLayout, 600, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/maxiflexy/minibankapp/css/banking-style.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createAccountCreationLayout() {
        // Welcome header
        Label welcomeLabel = new Label("WELCOME TO DREAM-DEVs BANKING APPLICATION");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        welcomeLabel.setTextFill(Color.DARKBLUE);

        // Account creation form
        Label nameLabel = new Label("Enter account holder name:");
        nameField = new TextField();
        nameField.setMaxWidth(300);
        nameField.setPromptText("e.g. John Doe");

        Label depositLabel = new Label("Enter initial deposit amount:");
        initialDepositField = new TextField();
        initialDepositField.setMaxWidth(300);
        initialDepositField.setPromptText("e.g. 1000.00");

        Button createAccountButton = new Button("Create Account");
        createAccountButton.getStyleClass().add("action-button");
        createAccountButton.setOnAction(e -> createAccount());

        // Layout arrangement
        accountCreationLayout = new VBox(15);
        accountCreationLayout.setAlignment(Pos.CENTER);
        accountCreationLayout.setPadding(new Insets(30));
        accountCreationLayout.getStyleClass().add("panel");

        // Add decorative separator
        Separator separator = new Separator();
        separator.setPrefWidth(400);

        accountCreationLayout.getChildren().addAll(
                welcomeLabel,
                separator,
                nameLabel,
                nameField,
                depositLabel,
                initialDepositField,
                createAccountButton
        );
    }

    private void createBankingOperationsLayout() {
        // Header for operations panel
        Label operationsLabel = new Label("Banking Operations");
        operationsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        operationsLabel.setTextFill(Color.DARKBLUE);

        // Account info section
        balanceLabel = new Label("Current Balance: $0.00");
        balanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        balanceLabel.getStyleClass().add("balance-label");

        // Transaction section
        Label amountLabel = new Label("Enter amount:");
        amountField = new TextField();
        amountField.setMaxWidth(300);
        amountField.setPromptText("e.g. 100.00");

        // Action buttons
        Button depositButton = new Button("Deposit");
        depositButton.getStyleClass().add("deposit-button");
        depositButton.setOnAction(e -> deposit());

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.getStyleClass().add("withdraw-button");
        withdrawButton.setOnAction(e -> withdraw());

        Button displayButton = new Button("Display Account Details");
        displayButton.getStyleClass().add("display-button");
        displayButton.setOnAction(e -> displayAccountDetails());

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(depositButton, withdrawButton, displayButton);

        // Transaction history area
        Label historyLabel = new Label("Transaction History:");
        transactionHistoryArea = new TextArea();
        transactionHistoryArea.setEditable(false);
        transactionHistoryArea.setPrefHeight(300);
        transactionHistoryArea.getStyleClass().add("transaction-history");

        // Exit button
        Button exitButton = new Button("Exit Application");
        exitButton.getStyleClass().add("exit-button");
        exitButton.setOnAction(e -> System.exit(0));

        // Layout arrangement
        bankingOperationsLayout = new VBox(15);
        bankingOperationsLayout.setAlignment(Pos.CENTER);
        bankingOperationsLayout.setPadding(new Insets(20));
        bankingOperationsLayout.getStyleClass().add("panel");

        // Add separators for visual distinction
        Separator topSeparator = new Separator();
        Separator midSeparator = new Separator();

        bankingOperationsLayout.getChildren().addAll(
                operationsLabel,
                topSeparator,
                balanceLabel,
                amountLabel,
                amountField,
                buttonBox,
                midSeparator,
                historyLabel,
                transactionHistoryArea,
                exitButton
        );
    }

    private void createAccount() {
        try {
            String name = nameField.getText().trim();
            String depositText = initialDepositField.getText().trim();

            if (name.isEmpty()) {
                showAlert("Account name cannot be empty!");
                return;
            }

            if (depositText.isEmpty()) {
                showAlert("Initial deposit amount cannot be empty!");
                return;
            }

            double initialDeposit = Double.parseDouble(depositText);

            if (initialDeposit <= 0) {
                showAlert("Initial deposit must be greater than zero!");
                return;
            }

            account = new BankAccount(name, initialDeposit);
            updateBalanceLabel();

            // Switch to banking operations layout
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(bankingOperationsLayout);

            // Display initial message
            transactionHistoryArea.setText("Account created successfully!\n");
            displayAccountDetails();
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number for the deposit amount!");
        }
    }

    private void deposit() {
        if (validateAmount()) {
            double amount = Double.parseDouble(amountField.getText().trim());
            String result = account.deposit(amount);
            transactionHistoryArea.appendText(result + "\n");
            updateBalanceLabel();
            amountField.clear();
        }
    }

    private void withdraw() {
        if (validateAmount()) {
            double amount = Double.parseDouble(amountField.getText().trim());
            String result = account.withdraw(amount);
            transactionHistoryArea.appendText(result + "\n");
            updateBalanceLabel();
            amountField.clear();
        }
    }

    private boolean validateAmount() {
        try {
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                showAlert("Please enter an amount!");
                return false;
            }

            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showAlert("Amount must be greater than zero!");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number!");
            return false;
        }
    }

    private void displayAccountDetails() {
        String details = account.getAccountDetails();
        transactionHistoryArea.appendText("\n--- ACCOUNT DETAILS ---\n" + details + "\n\n");
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Current Balance: $%.2f", account.getBalance()));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}