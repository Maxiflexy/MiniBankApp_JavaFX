package com.maxiflexy.minibankapp.controller;

import com.maxiflexy.minibankapp.model.BankAccount;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BankingOperationsController implements Initializable {

    @FXML
    private Label accountHolderLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private TextField amountField;

    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button displayButton;

    @FXML
    private Label transactionStatusLabel;

    @FXML
    private TextArea transactionHistoryArea;

    @FXML
    private Button exitButton;

    private BankAccount account;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any initialization code goes here
    }

    public void initData(BankAccount account) {
        this.account = account;
        accountHolderLabel.setText(account.getAccountHolder());
        updateBalanceLabel();
        displayTransactionHistory();
    }

    @FXML
    private void handleDeposit(ActionEvent event) {
        if (validateAmount()) {
            double amount = Double.parseDouble(amountField.getText().trim());
            String result = account.deposit(amount);

            if (!result.startsWith("ERROR")) {
                showSuccessStatus(result);
                updateBalanceLabel();
                displayTransactionHistory();
                amountField.clear();
            } else {
                showErrorStatus(result);
            }
        }
    }

    @FXML
    private void handleWithdraw(ActionEvent event) {
        if (validateAmount()) {
            double amount = Double.parseDouble(amountField.getText().trim());
            String result = account.withdraw(amount);

            if (!result.startsWith("ERROR")) {
                showSuccessStatus(result);
                updateBalanceLabel();
                displayTransactionHistory();
                amountField.clear();
            } else {
                showErrorStatus(result);
            }
        }
    }

    @FXML
    private void displayAccountDetails(ActionEvent event) {
        StringBuilder details = new StringBuilder();
        details.append("\n--- ACCOUNT DETAILS ---\n");
        details.append("Account Holder: ").append(account.getAccountHolder()).append("\n");
        details.append("Current Balance: ").append(account.getFormattedBalance()).append("\n");
        details.append("Total Transactions: ").append(account.getTransactionCount()).append("\n\n");

        transactionHistoryArea.appendText(details.toString());
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        Platform.exit();
    }

    private boolean validateAmount() {
        try {
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                showErrorStatus("Please enter an amount!");
                return false;
            }

            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                showErrorStatus("Amount must be greater than zero!");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            showErrorStatus("Please enter a valid number!");
            return false;
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: " + account.getFormattedBalance());
    }

    private void displayTransactionHistory() {
        List<String> history = account.getTransactionHistory();
        transactionHistoryArea.clear();
        for (String transaction : history) {
            transactionHistoryArea.appendText(transaction + "\n");
        }
    }

    private void showSuccessStatus(String message) {
        transactionStatusLabel.setText(message);
        transactionStatusLabel.setTextFill(Color.valueOf("#2ecc71"));
        transactionStatusLabel.setVisible(true);

        // Hide the message after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> transactionStatusLabel.setVisible(false));
        pause.play();
    }

    private void showErrorStatus(String message) {
        transactionStatusLabel.setText(message);
        transactionStatusLabel.setTextFill(Color.valueOf("#e74c3c"));
        transactionStatusLabel.setVisible(true);

        // Hide the message after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> transactionStatusLabel.setVisible(false));
        pause.play();
    }
}
