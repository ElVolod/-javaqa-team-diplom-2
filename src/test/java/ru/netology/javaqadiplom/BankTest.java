package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    public void shouldTransferBetweenSavingAccounts() {
        Bank bank = new Bank();
        SavingAccount from = new SavingAccount(5_000, 1_000, 10_000, 5);
        SavingAccount to = new SavingAccount(2_000, 0, 10_000, 3);

        boolean result = bank.transfer(from, to, 2_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(3_000, from.getBalance());
        Assertions.assertEquals(4_000, to.getBalance());
    }

    @Test
    public void shouldTransferFromSavingToCreditAccount() {
        Bank bank = new Bank();
        SavingAccount from = new SavingAccount(5_000, 1_000, 10_000, 5);
        CreditAccount to = new CreditAccount(0, 10_000, 10);

        boolean result = bank.transfer(from, to, 3_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(2_000, from.getBalance());
        Assertions.assertEquals(3_000, to.getBalance());
    }

    @Test
    public void shouldNotTransferWhenFromBalanceInsufficient() {
        Bank bank = new Bank();
        SavingAccount from = new SavingAccount(2_000, 1_000, 10_000, 5);
        SavingAccount to = new SavingAccount(0, 0, 10_000, 3);

        boolean result = bank.transfer(from, to, 2_000);

        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, from.getBalance());
        Assertions.assertEquals(0, to.getBalance());
    }

    @Test
    public void shouldNotTransferWhenToAccountCannotReceive() {
        Bank bank = new Bank();
        SavingAccount from = new SavingAccount(5_000, 0, 10_000, 5);
        SavingAccount to = new SavingAccount(9_000, 0, 10_000, 3);

        boolean result = bank.transfer(from, to, 3_000);

        Assertions.assertFalse(result);
        Assertions.assertEquals(5_000, from.getBalance());
        Assertions.assertEquals(9_000, to.getBalance());
    }

    @Test
    public void shouldNotTransferNegativeAmount() {
        Bank bank = new Bank();
        SavingAccount from = new SavingAccount(5_000, 0, 10_000, 5);
        SavingAccount to = new SavingAccount(2_000, 0, 10_000, 3);

        boolean result = bank.transfer(from, to, -500);

        Assertions.assertFalse(result);
        Assertions.assertEquals(5_000, from.getBalance());
        Assertions.assertEquals(2_000, to.getBalance());
    }

    @Test
    public void shouldNotTransferZeroAmount() {
        Bank bank = new Bank();
        SavingAccount from = new SavingAccount(5_000, 0, 10_000, 5);
        SavingAccount to = new SavingAccount(2_000, 0, 10_000, 3);

        boolean result = bank.transfer(from, to, 0);

        Assertions.assertFalse(result);
        Assertions.assertEquals(5_000, from.getBalance());
        Assertions.assertEquals(2_000, to.getBalance());
    }

    @Test
    public void shouldTransferFromCreditToSavingAccount() {
        Bank bank = new Bank();
        CreditAccount from = new CreditAccount(3_000, 5_000, 10);
        SavingAccount to = new SavingAccount(1_000, 0, 10_000, 3);

        boolean result = bank.transfer(from, to, 3_000);

        Assertions.assertTrue(result);
        Assertions.assertEquals(0, from.getBalance());
        Assertions.assertEquals(4_000, to.getBalance());
    }
}
