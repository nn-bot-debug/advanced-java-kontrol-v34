package ua.kazmirchuk;

import java.util.HashSet;
import java.util.Set;

public class StandardOrderProcessor extends OrderProcessorTemplate {

    @Override
    protected void validate(Order order) {
        Set<String> uniqueProducts = new HashSet<>();
        for (OrderItem item : order.getItems()) {
            if (!uniqueProducts.add(item.getProductId())) {
                throw new BusinessValidException("Duplicate productId found: " + item.getProductId());
            }
        }
        order.setStatus(Order.OrderStatus.VALIDATED);
    }

    @Override
    protected void validatePromoCode(Order order) {
        String promo = order.getPromoCode();
        if (promo != null && !promo.equals("SPRING15")) {
            throw new InvalidPromoCodeException(promo);
        }
    }

    @Override
    protected Money calculateTotal(Order order) {
        double sum = 0;
        String currency = "UAH";
        for (OrderItem item : order.getItems()) {
            sum = sum + item.getPrice().getAmount();
            currency = item.getPrice().getCurrency();
        }

        // Знижка 15% за SPRING15
        if ("SPRING15".equals(order.getPromoCode())) {
            sum = sum * 0.85;
        }

        return new Money(sum, currency);
    }
}
