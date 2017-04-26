package com.bddinaction.serenitybank.accounts;

import com.bddinaction.serenitybank.deposits.DepositFee;
import com.bddinaction.serenitybank.model.AccountType;
import com.bddinaction.serenitybank.model.BankAccount;
import com.bddinaction.serenitybank.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.bddinaction.serenitybank.accounts.AccountOrdering.firstOf;
import static com.bddinaction.serenitybank.accounts.AccountOrdering.lastOf;

public class AccountService {

    Map<String, BankAccount> accounts = new ConcurrentHashMap<>();

    AtomicInteger accountNumberCounter = new AtomicInteger();

    public String createNewAccount(AccountType type, BigDecimal initialDeposit, LocalDate creationDate) {
        String accountNumber = Integer.toString(accountNumberCounter.incrementAndGet());
        BankAccount newAccount = new BankAccount(accountNumber, type);
        newAccount.deposit(new Transaction(initialDeposit, creationDate, "deposit", initialDeposit));
        accounts.put(accountNumber, newAccount);
        return accountNumber;
    }

    public String createNewAccount(String accountNumber, AccountType type, BigDecimal initialDeposit) {
        BankAccount newAccount = new BankAccount(accountNumber, type);
        newAccount.deposit(initialDeposit);
        accounts.put(accountNumber, newAccount);
        return accountNumber;
    }

    public void transferFunds(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

        BankAccount fromAccount = accounts.get(fromAccountNumber);
        BankAccount toAccount = accounts.get(toAccountNumber);

        synchronized (firstOf(fromAccount, toAccount)) {
            synchronized (lastOf(fromAccount, toAccount)) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            }
        }
    }

    public BigDecimal getBalance(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        return account.getBalance();
    }

    public void makeDeposit(String accountNumber, BigDecimal amount, LocalDate depositDate) {
        BankAccount account = accounts.get(accountNumber);
        account.deposit(new Transaction(amount, depositDate, "deposit"));
        account.withdraw(DepositFee.forAccountType(account.getType()).apply(amount));
    }

    //todo : to remove one day
    public void makeDeposit(String accountNumber, BigDecimal amount) {
        BankAccount account = accounts.get(accountNumber);
        account.deposit(amount);
        account.withdraw(DepositFee.forAccountType(account.getType()).apply(amount));
    }

    public String createNewAccount(AccountType accountType, BigDecimal initialBalance) {
        String accountNumber = Integer.toString(accountNumberCounter.incrementAndGet());
        return createNewAccount(accountNumber, accountType, initialBalance);
    }

    public void printTransactions(String accountNumber, TransactionPrinter printer) {
        BankAccount account = accounts.get(accountNumber);
        account.getTransactions().forEach(printer::add);
    }

    public void makeWithdraw(String accountNumber, BigDecimal amount, LocalDate withdrawDate) {
        BankAccount account = accounts.get(accountNumber);
        account.withdraw(new Transaction(amount, withdrawDate, "withdrawal"));
    }

}
