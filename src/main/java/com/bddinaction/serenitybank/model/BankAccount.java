package com.bddinaction.serenitybank.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private final String number;
    private final AccountType type;
    private BigDecimal balance;

    private final Object lock = new Object();
    private List<Transaction> transactions = new ArrayList<>();

    public BankAccount(String accountNumber, AccountType accountType) {
        this.number = accountNumber;
        this.type = accountType;
        this.balance = BigDecimal.ZERO;
    }

    public String getNumber() {
        return number;
    }

    public AccountType getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        synchronized (lock) {
            balance = balance.add(amount);
        }
    }

    public void withdraw(BigDecimal amount) {
        synchronized (lock) {
            balance = balance.subtract(amount);
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(Transaction transaction) {
        deposit(transaction.getAmount());
        transactions.add(new Transaction(transaction.getAmount(), transaction.getDate(), transaction.getType(), balance));
    }

    public void withdraw(Transaction transaction) {
        withdraw(transaction.getAmount());
        transactions.add(new Transaction(transaction.getAmount().negate(), transaction.getDate(), transaction.getType(), balance));
    }
}
