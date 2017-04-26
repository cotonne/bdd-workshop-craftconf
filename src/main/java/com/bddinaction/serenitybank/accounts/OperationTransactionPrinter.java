package com.bddinaction.serenitybank.accounts;

import com.bddinaction.serenitybank.model.Transaction;

import java.util.stream.Collectors;

/**
 * Created by yvan on 26/04/17.
 */
public class OperationTransactionPrinter extends TransactionPrinter {
    public String toString() {
        return dates.stream()
                .map(Transaction::getType)
                .collect(Collectors.joining("\n"));
    }
}
