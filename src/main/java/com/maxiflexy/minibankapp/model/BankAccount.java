package com.maxiflexy.minibankapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private final String accountHolder;
    private double balance;
    private final List<String> transactionHistory;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();

        // Record initial deposit
        String initialTransaction = String.format("[%s] ACCOUNT CREATED with initial deposit of $%.2f",
                getCurrentTime(), initialBalance);
        transactionHistory.add(initialTransaction);
    }

    public String deposit(double amount) {
        if (amount <= 0) {
            return "ERROR: Deposit amount must be greater than zero.";
        }

        balance += amount;
        String transaction = String.format("[%s] DEPOSIT: $%.2f - New Balance: $%.2f",
                getCurrentTime(), amount, balance);
        transactionHistory.add(transaction);
        return "Successfully deposited $" + String.format("%.2f", amount);
    }

    public String withdraw(double amount) {
        if (amount <= 0) {
            return "ERROR: Withdrawal amount must be greater than zero.";
        }
        if (amount > balance) {
            return String.format("ERROR: Insufficient balance. Current balance is $%.2f", balance);
        }

        balance -= amount;
        String transaction = String.format("[%s] WITHDRAWAL: $%.2f - New Balance: $%.2f",
                getCurrentTime(), amount, balance);
        transactionHistory.add(transaction);
        return "Successfully withdrew $" + String.format("%.2f", amount);
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    // Getters
    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // return a copy for safety
    }

    public String getFormattedBalance() {
        return String.format("$%.2f", balance);
    }

    public int getTransactionCount() {
        return transactionHistory.size();
    }
}