package ua.kazmirchuk;

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(Money money) throws PaymentInfrastructureException {
        if (money.getAmount() < 400) {
            throw new RuntimeException("PayPal minimum amount is 400. Current: " + money.getAmount());
        }
        System.out.println("Успішно оплачено через PayPal: " + money);
    }
}


