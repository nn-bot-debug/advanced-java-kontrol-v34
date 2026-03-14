import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kazmirchuk.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private StandardOrderProcessor processor;
    private OrderRepositoryService repo;

    @BeforeEach
    void setUp() {
        processor = new StandardOrderProcessor();
        repo = new OrderRepositoryService(processor);
    }

    @Test
    void testSuccessfulCardPayment() {
        OrderItem[] items = { new OrderItem("prod1", new Money(1000.0)) };
        Order order = new Order(items);

        assertDoesNotThrow(() -> processor.process(order, new CardPayment()));
        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void testPromoCodeSpring15Applied() {
        OrderItem[] items = { new OrderItem("prod1", new Money(1000.0)) };
        Order order = new Order(items, "SPRING15");

        assertDoesNotThrow(() -> processor.process(order, new CardPayment()));
        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void testOptionalFindById() {
        Order order = new Order(new OrderItem[0]);
        repo.save(order);

        Optional<Order> found = repo.findById(order.getId());
        assertTrue(found.isPresent());
        assertEquals(order.getId(), found.get().getId());
    }

    @Test
    void testStateTransitionPaidToRefunded() {
        Order order = new Order(new OrderItem[0]);
        order.setStatus(Order.OrderStatus.PAID);
        order.refund();

        assertEquals(Order.OrderStatus.REFUNDED, order.getStatus());
    }

    @Test
    void testValidPayPalAmounts() {
        OrderItem[] items = { new OrderItem("prod1", new Money(400)) };
        Order order = new Order(items);

        assertDoesNotThrow(() -> processor.process(order, new PayPalPayment()));
        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void testDuplicateProductIdValidation() {
        OrderItem[] items = {
                new OrderItem("duplicate-id", new Money(100.0)),
                new OrderItem("duplicate-id", new Money(200.0))
        };
        Order order = new Order(items);

        assertThrows(RuntimeException.class, () -> processor.process(order, new CardPayment()));
        assertEquals(Order.OrderStatus.FAILED, order.getStatus());
    }

    @Test
    void testInvalidPromoCodeThrowsException() {
        OrderItem[] items = { new OrderItem("prod1", new Money(100.0)) };
        Order order = new Order(items, "FAKE_PROMO"); // Неправильний промокод

        assertThrows(InvalidPromoCodeException.class, () -> processor.process(order, new CardPayment()));
        assertEquals(Order.OrderStatus.FAILED, order.getStatus());
    }

    @Test
    void testCardPaymentLimitExceeded() {
        OrderItem[] items = { new OrderItem("prod1", new Money(35000.01)) }; // На 1 копійку більше
        Order order = new Order(items);

        assertThrows(RuntimeException.class, () -> processor.process(order, new CardPayment()));
    }

    @Test
    void testPayPalMinimumAmountNotMet() {
        OrderItem[] items = { new OrderItem("prod1", new Money(399.99)) }; // Менше мінімуму
        Order order = new Order(items);

        assertThrows(RuntimeException.class, () -> processor.process(order, new PayPalPayment()));
    }

    @Test
    void testBankTransferExceptionChaining() {
        OrderItem[] items = { new OrderItem("prod1", new Money(1000.0)) };
        Order order = new Order(items);

        AppException exception = assertThrows(AppException.class, () -> processor.process(order, new BankTransferPayment()));

        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof PaymentInfrastructureException);
    }
}