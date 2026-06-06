package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

    // ==================== Constructor Tests ====================

    @Test
    public void shouldThrowWhenRateNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SavingAccount(1_000, 500, 5_000, -1));
    }

    @Test
    public void shouldThrowWhenMinBalanceNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SavingAccount(1_000, -100, 5_000, 5));
    }

    @Test
    public void shouldThrowWhenMinBalanceGreaterThanMax() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SavingAccount(1_000, 5_000, 1_000, 5));
    }

    @Test
    public void shouldThrowWhenInitialBalanceLessThanMin() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SavingAccount(200, 500, 5_000, 5));
    }

    @Test
    public void shouldThrowWhenInitialBalanceGreaterThanMax() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new SavingAccount(6_000, 500, 5_000, 5));
    }

    @Test
    public void shouldCreateAccountWithValidParams() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    // ==================== pay() Tests ====================

    @Test
    public void shouldPaySuccessfully() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.pay(500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(1_500, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenBalanceWouldGoBelowMin() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.pay(1_500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldPayWhenBalanceEqualsMinAfterPay() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.pay(1_000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayNegativeAmount() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.pay(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldNotPayZeroAmount() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.pay(0);
        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldNotChangeBalanceOnFailedPay() {
        SavingAccount account = new SavingAccount(1_500, 1_000, 10_000, 5);
        account.pay(1_000);
        Assertions.assertEquals(1_500, account.getBalance());
    }

    // ==================== add() Tests ====================

    @Test
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.add(3_000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(5_000, account.getBalance());
    }

    @Test
    public void shouldAddUpToMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.add(8_000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(10_000, account.getBalance());
    }

    @Test
    public void shouldNotAddWhenExceedsMaxBalance() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.add(9_000);
        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldNotAddNegativeAmount() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.add(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldNotAddZeroAmount() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 10_000, 5);
        boolean result = account.add(0);
        Assertions.assertFalse(result);
        Assertions.assertEquals(2_000, account.getBalance());
    }

    // ==================== yearChange() Tests ====================

    @Test
    public void shouldReturnCorrectYearChange() {
        SavingAccount account = new SavingAccount(200, 100, 10_000, 15);
        Assertions.assertEquals(30, account.yearChange());
    }

    @Test
    public void shouldReturnZeroYearChangeWhenBalanceZero() {
        SavingAccount account = new SavingAccount(0, 0, 10_000, 15);
        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    public void shouldReturnCorrectYearChangeWithRounding() {
        SavingAccount account = new SavingAccount(1_000, 0, 10_000, 7);
        Assertions.assertEquals(70, account.yearChange());
    }
}
