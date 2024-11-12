package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionEngineTest {
    private Transaction firstTransaction;

    @BeforeEach
    void setup() {
        firstTransaction=new Transaction();
        firstTransaction.setTransactionId(1);
        firstTransaction.setAccountId(2);
        firstTransaction.setAmount(100);
        firstTransaction.setDebit(true);
    }

    @Test
    void equals_transactionsAreEqual_returnsTrue() {
        Transaction secondTransaction=new Transaction();
        secondTransaction.setTransactionId(firstTransaction.getTransactionId());
        secondTransaction.setAccountId(firstTransaction.getAccountId());
        secondTransaction.setAmount(firstTransaction.getAmount());
        secondTransaction.setDebit(firstTransaction.isDebit());

        Assertions.assertTrue(firstTransaction.equals(secondTransaction));
    }

    @Test
    void equals_transactionsHaveDifferentID_returnsFalse() {
        Transaction secondTransaction=new Transaction();
        secondTransaction.setTransactionId(firstTransaction.getTransactionId()+1);
        secondTransaction.setAccountId(firstTransaction.getAccountId());
        secondTransaction.setAmount(firstTransaction.getAmount());
        secondTransaction.setDebit(firstTransaction.isDebit());

        Assertions.assertFalse(firstTransaction.equals(secondTransaction));
    }

    @Test
    void equals_otherObjectIsNotTransaction_returnsFalse() {
        int intObject=1;

        Assertions.assertFalse(firstTransaction.equals(intObject));
    }
}
