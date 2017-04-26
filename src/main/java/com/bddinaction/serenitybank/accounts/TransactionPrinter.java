package com.bddinaction.serenitybank.accounts;

import com.bddinaction.serenitybank.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by yvan on 26/04/17.
 */
public class TransactionPrinter {
    protected List<Transaction> dates = new ArrayList<>();

    @Override
    public String toString() {
        return dates.stream()
                .map(Transaction::getDate)
                .map(LocalDate::toString)
                .collect(Collectors.joining("\n"));
    }

    public void add(Transaction transaction) {
        dates.add(transaction);

    }
}
