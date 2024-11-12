package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionEngineTest {
    @Test
    void addTransactionAndDetectFraud_correctTransactionWithFraud_addsTransactionAndReturnsFraudScore() {
        Transaction transaction=new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(2);
        transaction.setAmount(100);
        transaction.setDebit(true);

        TransactionEngine transactionEngine=new TransactionEngine();

        Assertions.assertEquals(100, transactionEngine.addTransactionAndDetectFraud(transaction));
        Assertions.assertTrue(transactionEngine.transactionHistory.contains(transaction));
    }
    @Test
    void addTransactionAndDetectFraud_repeatedTransaction_returnsZero() {
        Transaction transaction=new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(2);
        transaction.setAmount(100);
        transaction.setDebit(true);

        TransactionEngine transactionEngine=new TransactionEngine();
        transactionEngine.addTransactionAndDetectFraud(transaction);
        Assertions.assertEquals(0, transactionEngine.addTransactionAndDetectFraud(transaction));
    }

    @Test
    void detectFraudulentTransaction_transactionNotDebit_returnsZero() {
        Transaction transaction=new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(2);
        transaction.setAmount(100);
        transaction.setDebit(false);

        TransactionEngine transactionEngine=new TransactionEngine();

        Assertions.assertEquals(0, transactionEngine.detectFraudulentTransaction(transaction));
    }

    @Test
    void detectFraudulentTransaction_transactionHasExcessiveDebit_returnsZero() {
        Transaction transaction=new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(2);
        transaction.setAmount(1200);
        transaction.setDebit(true);

        Transaction fakeTransaction=new Transaction();
        fakeTransaction.setTransactionId(1);
        fakeTransaction.setAccountId(2);
        fakeTransaction.setAmount(1200);
        fakeTransaction.setDebit(true);

        TransactionEngine transactionEngine=new TransactionEngine();
        transactionEngine.addTransactionAndDetectFraud(transaction);
        fakeTransaction.setAmount(2500);

        Assertions.assertEquals(100, transactionEngine.detectFraudulentTransaction(fakeTransaction));
    }


    @Test
    void detectFraudulentTransaction_transactionIsCorrect_returnsZero() {
        Transaction transaction=new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(2);
        transaction.setAmount(1200);
        transaction.setDebit(true);

        TransactionEngine transactionEngine=new TransactionEngine();
        transactionEngine.addTransactionAndDetectFraud(transaction);

        Assertions.assertEquals(0, transactionEngine.detectFraudulentTransaction(transaction));
    }
}
