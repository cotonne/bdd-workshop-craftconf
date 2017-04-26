package com.bddinaction.serenitybank.glue;

import com.bddinaction.serenitybank.accounts.AccountService;
import com.bddinaction.serenitybank.accounts.FullTransactionPrinter;
import com.bddinaction.serenitybank.accounts.TransactionFormat;
import com.bddinaction.serenitybank.model.AccountType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by yvan on 26/04/17.
 */
public class RecordTransactionsStep {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private com.bddinaction.serenitybank.accounts.AccountService accountService = new AccountService();
    private String accountNumber;

    @Given("^Joe opened a (.*) account on (.*) with balance €(\\d+)$")
    public void joeOpenedACurrentAccountOnWithBalance€(AccountType accountType, String depositDate, int amount) throws Throwable {
        LocalDate transactionDate = LocalDate.parse(depositDate, formatter);
        accountNumber = accountService.createNewAccount(accountType, BigDecimal.valueOf(amount), transactionDate);
    }


    @When("^he deposits €(\\d+) into his account on (.*)$")
    public void heDeposits€IntoHisAccountOn(int amount, String date) throws Throwable {
        LocalDate transactionDate = LocalDate.parse(date, formatter);
        accountService.makeDeposit(accountNumber, BigDecimal.valueOf(amount), transactionDate);

    }

    @When("^he withdraws €(\\d+) from his account on (.*)$")
    public void heWithdraws€FromHisAccountOn(int amount, String date) throws Throwable {
        LocalDate transactionDate = LocalDate.parse(date, formatter);
        accountService.makeWithdraw(accountNumber, BigDecimal.valueOf(amount), transactionDate);
    }

    @Then("^his transaction history should include:$")
    public void hisTransactionHistoryShouldInclude(List<TransactionFormat> transactionHistory) throws Throwable {
        FullTransactionPrinter printer = new FullTransactionPrinter();
        accountService.printTransactions(accountNumber, printer);
        Assert.assertEquals(transactionHistory, printer.asList());
    }
}
