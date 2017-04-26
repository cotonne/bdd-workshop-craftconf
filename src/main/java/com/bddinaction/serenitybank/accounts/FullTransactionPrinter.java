package com.bddinaction.serenitybank.accounts;

import com.bddinaction.serenitybank.model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FullTransactionPrinter extends TransactionPrinter {
    public List<TransactionFormat> asList() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(dates);
        Collections.reverse(transactions);
        return transactions.stream()
                .map(Transaction::toTransactionFormat)
                .collect(Collectors.toList());
    }
}
