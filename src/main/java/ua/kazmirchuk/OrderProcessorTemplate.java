package ua.kazmirchuk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class OrderProcessorTemplate {
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessorTemplate.class.getName());

    public final void process(Order order, PaymentMethod paymentMethod) {
        try {
            logger.info("Started processing order: " + order.getId());
            validate(order);
            validatePromoCode(order);
            Money total = calculateTotal(order);
            pay(order, total, paymentMethod);
            complete(order);
            logger.info("Order successfully completed: " + order.getId());
        } catch (AppException e) {
            logger.warn("Business error during processing: " + e.getMessage());
            order.setStatus(Order.OrderStatus.FAILED);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected infrastructure failure");
            order.setStatus(Order.OrderStatus.FAILED);
            throw new AppException("Processing failed due to infrastructure", e);
        }
    }

    protected abstract void validate(Order order);
    protected abstract void validatePromoCode(Order order);
    protected abstract Money calculateTotal(Order order);

    protected void pay(Order order, Money total, PaymentMethod paymentMethod) throws Exception {
        logger.info("Charging " + total);
        paymentMethod.pay(total);
        order.setStatus(Order.OrderStatus.PAID);
    }

    protected void complete(Order order) {
        order.setStatus(Order.OrderStatus.COMPLETED);
    }
}

