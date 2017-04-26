package com.bddinaction.serenitybank.accounts

import com.bddinaction.serenitybank.model.AccountType
import spock.lang.Specification

import java.time.LocalDate

/**
 * Created by yvan on 26/04/17.
 */
class WhenDepositingFunds  extends Specification{
    def "depositing funds into an account"() {
        given:
            def accountService = new AccountService ()
            def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0)
            def depositDate = LocalDate.parse ("2017-07-24")
        when:
            accountService.makeDeposit(accountNumber, 50.00, depositDate)
        then:
            accountService.getBalance(accountNumber) == 150.00
    }

    def "depositing funds into an account at a precise day"() {
        given:
            def accountService = new AccountService ()
            def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0)
            def transactionDate = LocalDate.parse ("2017-07-24")
        when:
            accountService.makeDeposit(accountNumber, 50.00, transactionDate)

        then:
            def printer = new TransactionPrinter()
            accountService.printTransactions(accountNumber, printer)
            printer.toString() == "2017-07-24"
    }

    def "withdrawing funds from an account"() {
        given:
            def accountService = new AccountService ()
            def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0)
            def transactionDate = LocalDate.parse ("2017-07-24")
        when:
            accountService.makeWithdraw(accountNumber, 50.00, transactionDate)
        then:
            accountService.getBalance(accountNumber) == 50.00
    }

    def "withdrawing funds from an account on a precise date"() {
        given:
            def accountService = new AccountService ()
            def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0)
            def transactionDate = LocalDate.parse ("2017-07-24")
        when:
            accountService.makeWithdraw(accountNumber, 50.00, transactionDate)
        then:
            def printer = new TransactionPrinter()
            accountService.printTransactions(accountNumber, printer)
            printer.toString() == "2017-07-24"
    }

    def "initial deposit should have a precise date"() {
        given:
        def accountService = new AccountService ()
        def transactionDate = LocalDate.parse ("2017-07-24")
        when:
        def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0, transactionDate)
        then:
        def printer = new TransactionPrinter()
        accountService.printTransactions(accountNumber, printer)
        printer.toString() == "2017-07-24"
    }

    def "withdrawing should labeled qs withdrawing "() {
        given:
            def accountService = new AccountService ()
            def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0)
            def transactionDate = LocalDate.parse ("2017-07-24")
        when:
            accountService.makeWithdraw(accountNumber, 50.00, transactionDate)
        then:
        def printer = new OperationTransactionPrinter()
        accountService.printTransactions(accountNumber, printer)
        printer.toString() == "withdrawal"
    }

    def "depositing funds should be labelled"() {
        given:
        def accountService = new AccountService ()
        def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0)
        def transactionDate = LocalDate.parse ("2017-07-24")
        when:
        accountService.makeDeposit(accountNumber, 50.00, transactionDate)

        then:
        def printer = new OperationTransactionPrinter()
        accountService.printTransactions(accountNumber, printer)
        printer.toString() == "deposit"
    }

    def "initial deposit should be labelled as deposit"() {
        given:
        def accountService = new AccountService ()
        def transactionDate = LocalDate.parse ("2017-07-24")
        when:
        def accountNumber = accountService.createNewAccount(AccountType.Current, 100.0, transactionDate)
        then:
        def printer = new OperationTransactionPrinter()
        accountService.printTransactions(accountNumber, printer)
        printer.toString() == "deposit"
    }

}
