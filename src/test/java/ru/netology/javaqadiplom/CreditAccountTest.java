package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditAccountTest {

    // ==================== Constructor Tests ====================

    @Test
    public void shouldThrowWhenRateNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new CreditAccount(0, 5_000, -1));
    }

    @Test
    public void shouldCreateAccountWithZeroRate() {
        CreditAccount account = new CreditAccount(0, 5_000, 0);
        Assertions.assertEquals(0, account.getRate());
    }

    @Test
    public void shouldThrowWhenCreditLimitNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new CreditAccount(0, -1_000, 5));
    }

    @Test
    public void shouldThrowWhenInitialBalanceNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new CreditAccount(-500, 5_000, 5));
    }

    @Test
    public void shouldCreateAccountWithValidParams() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        Assertions.assertEquals(1_000, account.getBalance());
        Assertions.assertEquals(5_000, ((CreditAccount) account).creditLimit);
        Assertions.assertEquals(15, account.getRate());
    }

    // ==================== pay() Tests ====================

    @Test
    public void shouldPaySuccessfully() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.pay(500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(500, account.getBalance());
    }

    @Test
    public void shouldPayIntoCredit() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.pay(3_000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-2_000, account.getBalance());
    }

    @Test
    public void shouldPayExactlyToCreditLimit() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        boolean result = account.pay(5_000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-5_000, account.getBalance());
    }

    @Test
    public void shouldNotPayWhenExceedsCreditLimit() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        boolean result = account.pay(6_000);
        Assertions.assertFalse(result);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldNotChangeBalanceOnFailedPay() {
        CreditAccount account = new CreditAccount(1_000, 2_000, 15);
        account.pay(4_000);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayNegativeAmount() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.pay(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayZeroAmount() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.pay(0);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    // ==================== add() Tests ====================

    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        account.add(3_000);
        Assertions.assertEquals(3_000, account.getBalance());
    }

    @Test
    public void shouldAddToAccount() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.add(500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(1_500, account.getBalance());
    }

    @Test
    public void shouldAddAmountToCurrentBalance() {
        CreditAccount account = new CreditAccount(200, 100, 15);
        account.add(300);
        Assertions.assertEquals(500, account.getBalance());
    }

    @Test
    public void shouldAddToRepayDebt() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        account.pay(3_000);
        boolean result = account.add(1_000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-2_000, account.getBalance());
    }

    @Test
    public void shouldNotAddNegativeAmount() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.add(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldNotAddZeroAmount() {
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        boolean result = account.add(0);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1_000, account.getBalance());
    }

    // ==================== yearChange() Tests ====================

    @Test
    public void shouldCalculateYearChangeOnNegativeBalance() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        account.pay(200);
        Assertions.assertEquals(-30, account.yearChange());
    }

    @Test
    public void shouldNotCalculateYearChangeOnPositiveBalance() {
        CreditAccount account = new CreditAccount(200, 5_000, 15);
        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    public void shouldNotCalculateYearChangeOnZeroBalance() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Assertions.assertEquals(0, account.yearChange());
    }
}
