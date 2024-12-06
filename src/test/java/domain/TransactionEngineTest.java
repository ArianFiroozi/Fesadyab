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
    void getAverageTransactionAmountByAccount_multipleTransactionsByAccount_returnsCorrectAvg() {
        Transaction transaction2=new Transaction();
        transaction2.setAccountId(2);
        transaction2.setTransactionId(2);
        transaction2.setAmount(1200);
        transactionEngine.addTransactionAndDetectFraud(transaction);
        transactionEngine.addTransactionAndDetectFraud(transaction2);

        Assertions.assertEquals(650, transactionEngine.getAverageTransactionAmountByAccount(2));
    }

    @Test
    void detectFraudulentTransaction_multipleTransactionsByAccount_returnsCorrectAvg() {
        Transaction transaction2=new Transaction();
        transaction2.setAccountId(2);
        transaction2.setTransactionId(2);
        transaction2.setAmount(1200);
        transactionEngine.addTransactionAndDetectFraud(transaction);
        transactionEngine.addTransactionAndDetectFraud(transaction2);

        Assertions.assertEquals(650, transactionEngine.getAverageTransactionAmountByAccount(2));
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

    @Test
    void getTransactionPatternAboveThreshold_patternAboveThreshold_returnsDiff() {
        Transaction transaction2=new Transaction();
        transaction2.setAccountId(2);
        transaction2.setAmount(1200);

        transactionEngine.addTransactionAndDetectFraud(transaction);
        transactionEngine.addTransactionAndDetectFraud(transaction2);

        Assertions.assertEquals(1100, transactionEngine.getTransactionPatternAboveThreshold(1000));
    }

    @Test
    void getTransactionPatternAboveThreshold_patternUnderThreshold_returnsZero() {
        Transaction transaction2=new Transaction();
        transaction2.setAccountId(2);
        transaction2.setAmount(1200);

        transactionEngine.addTransactionAndDetectFraud(transaction);
        transactionEngine.addTransactionAndDetectFraud(transaction2);

        Assertions.assertEquals(0, transactionEngine.getTransactionPatternAboveThreshold(1200));
    }

    @Test
    void getTransactionPatternAboveThreshold_multipleTransactionsAboveThreshold_returnsZero() {
        Transaction transaction2=new Transaction();
        transaction2.setAccountId(2);
        transaction2.setTransactionId(2);
        transaction2.setAmount(1200);
        Transaction transaction3=new Transaction();
        transaction3.setTransactionId(3);
        transaction2.setAccountId(3);
        transaction3.setAmount(1900);

        transactionEngine.addTransactionAndDetectFraud(transaction);
        transactionEngine.addTransactionAndDetectFraud(transaction2);
        transactionEngine.addTransactionAndDetectFraud(transaction3);

        Assertions.assertEquals(0, transactionEngine.getTransactionPatternAboveThreshold(300));
    }

    @Test
    void getTransactionPatternAboveThreshold_multipleSamePatternTransactionsAboveThreshold_returnsCorrectAmount() {
        Transaction transaction2=new Transaction();
        transaction2.setAccountId(2);
        transaction2.setTransactionId(2);
        transaction2.setAmount(1200);
        Transaction transaction3=new Transaction();
        transaction3.setTransactionId(3);
        transaction2.setAccountId(3);
        transaction3.setAmount(2300);

        transactionEngine.addTransactionAndDetectFraud(transaction);
        transactionEngine.addTransactionAndDetectFraud(transaction2);
        transactionEngine.addTransactionAndDetectFraud(transaction3);

        Assertions.assertEquals(1100, transactionEngine.getTransactionPatternAboveThreshold(300));
    }

    @Test
    void getTransactionPatternAboveThreshold_HistIsEmpty_returnsZero() {
        Assertions.assertEquals(0, transactionEngine.getTransactionPatternAboveThreshold(300));
    }

}
