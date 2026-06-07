package ru.netology.javaqadiplom;

/**
 * Кредитный счёт
 * Может иметь баланс вплоть до отрицательного, но до указанного кредитного лимита.
 * Имеет ставку - количество процентов годовых на сумму на балансе, если она меньше нуля.
 */
public class CreditAccount extends Account {
    protected int creditLimit;

    /**
     * Создаёт новый объект кредитного счёта с заданными параметрами.
     * Если параметры некорректны (кредитный лимит отрицательный и так далее), то
     * должно выкидываться исключение вида IllegalArgumentException.
     * @param initialBalance - неотрицательное число, начальный баланс для счёта
     * @param creditLimit - неотрицательное число, максимальная сумма которую можно задолжать банку
     * @param rate - неотрицательное число, ставка кредитования для расчёта долга за отрицательный баланс
     */
    public CreditAccount(int initialBalance, int creditLimit, int rate) {
        if (rate < 0) {
            throw new IllegalArgumentException(
                    "Кредитная ставка не может быть отрицательной, а у вас: " + rate
            );
        }
        if (creditLimit < 0) {
            throw new IllegalArgumentException(
                    "Кредитный лимит не может быть отрицательным, а у вас: " + creditLimit
            );
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                    "Начальный баланс не может быть отрицательным, а у вас: " + initialBalance
            );
        }
        this.balance = initialBalance;
        this.creditLimit = creditLimit;
        this.rate = rate;
    }

    /**
     * Операция оплаты с карты на указанную сумму.
     * В результате успешной оплаты баланс должен уменьшиться на сумму покупки.
     * Оплата не должна проходить, если баланс после оплаты станет ниже лимита.
     * @param amount - сумма покупки
     * @return true если операция прошла успешно, false иначе.
     */
    @Override
    public boolean pay(int amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance - amount < -creditLimit) {
            return false;
        }
        balance = balance - amount;
        return true;
    }

    /**
     * Операция пополнения карты на указанную сумму.
     * В результате успешного пополнения баланс должен увеличиться на сумму пополнения.
     * Если сумма пополнения отрицательная или нулевая, то операция должна
     * завершиться вернув false и ничего не поменяв на счёте.
     * @param amount - сумма пополнения
     * @return true если операция прошла успешно, false иначе.
     */
    @Override
    public boolean add(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance = balance + amount;
        return true;
    }

    /**
     * Расчёт процентов на отрицательный баланс счёта за год.
     * Расчёт идёт в целых числах, доли отбрасываются.
     * Если баланс положительный или нулевой, то возвращается 0.
     * Если отрицательный, то возвращается количество процентов от баланса.
     * @return сумму процентов
     */
    @Override
    public int yearChange() {
        if (balance >= 0) {
            return 0;
        }
        return balance * rate / 100;
    }
}
