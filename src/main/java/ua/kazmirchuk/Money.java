package ua.kazmirchuk;

import java.util.Objects;

public class Money {

    private final double amount;
    private final String currency;

    public Money(double amount) {
        this(amount, "UAH");
    }

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return this.amount == money.amount && Objects.equals(this.currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() { return amount + " " + currency; }

}
