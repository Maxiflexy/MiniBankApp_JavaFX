package com.maxiflexy.minibankapp.model;

public class BankAccount {

    private final String accountHolder;
    private double balance;
    private int transactionCount;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionCount = 0;
    }

    public String deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionCount++;
            return String.format("DEPOSIT: Successfully deposited $%.2f", amount);
        } else {
            return "ERROR: Deposit amount must be greater than zero.";
        }
    }

    public String withdraw(double amount) {
        if (amount <= 0) {
            return "ERROR: Withdrawal amount must be greater than zero.";
        } else if (amount > balance) {
            return String.format("ERROR: Insufficient balance. Current balance is $%.2f", balance);
        } else {
            balance -= amount;
            transactionCount++;
            return String.format("WITHDRAWAL: Successfully withdrew $%.2f", amount);
        }
    }

    public String getAccountDetails() {
        return String.format("Account Holder: %s\n" +
                        "Current Balance: $%.2f\n" +
                        "Total Transactions: %d",
                accountHolder, balance, transactionCount);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
