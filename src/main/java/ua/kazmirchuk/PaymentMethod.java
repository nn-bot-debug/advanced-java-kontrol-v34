package ua.kazmirchuk;

public interface PaymentMethod {
    void pay(Money amount) throws PaymentInfrastructureException;
}

