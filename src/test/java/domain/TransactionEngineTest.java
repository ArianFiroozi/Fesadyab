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
}
