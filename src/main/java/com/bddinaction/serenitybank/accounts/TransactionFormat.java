package com.bddinaction.serenitybank.accounts;

import java.math.BigDecimal;

/**
 * Created by yvan on 26/04/17.
 */
public class TransactionFormat {
    BigDecimal amount;
    String description;
    String date;
    BigDecimal balance;

    public TransactionFormat(BigDecimal amount, String description, String date, BigDecimal balance) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionFormat that = (TransactionFormat) o;

        if (amount != null ? !(amount.compareTo(that.amount) == 0): that.amount != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return balance != null ? (balance.compareTo(that.balance) == 0) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionFormat{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", balance=" + balance +
                '}';
    }
}
