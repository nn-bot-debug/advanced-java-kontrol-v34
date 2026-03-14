package ua.kazmirchuk;

import java.io.IOException;

public class BankTransferPayment implements PaymentMethod {
    @Override
    public void pay(Money money) throws PaymentInfrastructureException {
        double amountWithCommission = money.getAmount() * 1.025;
        System.out.println("Спроба банківського переказу. Сума з комісією: " + amountWithCommission);

        try {
            boolean serverIsDown = true;
            if (serverIsDown) {
                throw new IOException("Bank server connection timeout");
            }
        } catch (IOException e) {
            throw new PaymentInfrastructureException("Помилка інфраструктури банку під час переказу", e);
        }
    }
}
