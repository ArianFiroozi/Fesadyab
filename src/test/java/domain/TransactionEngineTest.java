package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionEngineTest {
    private TransactionEngine transactionEngine;
    private Transaction transaction;

    @BeforeEach
    void setup() {
        transactionEngine=new TransactionEngine();
        transaction=new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(2);
        transaction.setAmount(100);
        transaction.setDebit(true);
    }

    @Test
    void addTransactionAndDetectFraud_correctTransactionWithFraud_addsTransactionAndReturnsFraudScore() {
        Assertions.assertEquals(100, transactionEngine.addTransactionAndDetectFraud(transaction));
        Assertions.assertTrue(transactionEngine.transactionHistory.contains(transaction));
    }
    @Test
    void addTransactionAndDetectFraud_repeatedTransaction_returnsZero() {
        transactionEngine.addTransactionAndDetectFraud(transaction);
        Assertions.assertEquals(0, transactionEngine.addTransactionAndDetectFraud(transaction));
    }

    @Test
    void detectFraudulentTransaction_transactionNotDebit_returnsZero() {
        transaction.setDebit(false);

        Assertions.assertEquals(0, transactionEngine.detectFraudulentTransaction(transaction));
    }

    @Test
    void detectFraudulentTransaction_transactionHasExcessiveDebit_returnsZero() {
        Transaction fakeTransaction=new Transaction();
        fakeTransaction.setTransactionId(1);
        fakeTransaction.setAccountId(2);
        fakeTransaction.setAmount(300);
        fakeTransaction.setDebit(true);

        transactionEngine.addTransactionAndDetectFraud(transaction);

        Assertions.assertEquals(100, transactionEngine.detectFraudulentTransaction(fakeTransaction));
    }


    @Test
    void detectFraudulentTransaction_transactionIsCorrect_returnsZero() {
        transactionEngine.addTransactionAndDetectFraud(transaction);

        Assertions.assertEquals(0, transactionEngine.detectFraudulentTransaction(transaction));
    }
}
