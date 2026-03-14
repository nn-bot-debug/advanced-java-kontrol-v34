package ua.kazmirchuk;

import java.util.Arrays;

public class Order {
    public enum OrderStatus {
        NEW, VALIDATED, PAID, REFUNDED, COMPLETED, FAILED
    }
    private final String id;
    private final OrderItem[] items;
    private final String promoCode;
    private OrderStatus status;
    private static int currentId = 1;


    public Order(OrderItem[] items) {
        this(items, null);
    }

    public Order(OrderItem[] items, String promoCode) {
        this.id = "order" + currentId++;
        this.items = items != null ? Arrays.copyOf(items, items.length) : new OrderItem[0];
        this.promoCode = promoCode;
        this.status = OrderStatus.NEW;
    }

    public String getId() {
        return id;
    }

    public OrderItem[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    public String getPromoCode() {
        return promoCode;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setStatus(OrderStatus status) { this.status = status; }

    public void refund() {
        if (this.status == OrderStatus.PAID) {
            this.status = OrderStatus.REFUNDED;
        } else {
            throw new IllegalStateException("Only PAID orders can be refunded");
        }
    }

    @Override
    public String toString() {
        return "Order{id=" + getId() +"| status=" + getStatus() +"}";
    }
}
