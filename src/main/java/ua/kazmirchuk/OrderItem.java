package ua.kazmirchuk;

public class OrderItem {
    private final String productId;
    private final Money price;

    public OrderItem(String productId, Money price) {
        this.productId = productId;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public Money getPrice() { return price; }
}
