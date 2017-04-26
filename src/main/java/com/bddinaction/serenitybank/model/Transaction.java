package com.bddinaction.serenitybank.model;

import com.bddinaction.serenitybank.accounts.TransactionFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by yvan on 26/04/17.
 */
public class Transaction {
    private final BigDecimal amount;
    private final LocalDate depositDate;
    private final String type;
    private final BigDecimal balance;

    public Transaction(BigDecimal amount, LocalDate depositDate, String type) {
        this.amount = amount;
        this.depositDate = depositDate;
        this.type = type;
        this.balance = null;
    }

    public Transaction(BigDecimal amount, LocalDate depositDate, String type, BigDecimal balance) {
        this.amount = amount;
        this.depositDate = depositDate;
        this.type = type;
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return depositDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionFormat toTransactionFormat() {
        return new TransactionFormat(amount, type, depositDate.toString(), balance);
    }
}
