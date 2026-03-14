package ua.kazmirchuk;

public class CardPayment implements PaymentMethod {
    @Override
    public void pay(Money money) throws PaymentInfrastructureException {
        if (money.getAmount() > 35000) {
            throw new RuntimeException("Card payment limit exceeded (max 35 000). Current: " + money.getAmount());
        }
        System.out.println("Успішно оплачено карткою: " + money);
    }
}
