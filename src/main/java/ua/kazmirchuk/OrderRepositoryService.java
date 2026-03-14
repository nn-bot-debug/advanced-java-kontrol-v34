package ua.kazmirchuk;

import java.util.Optional;

public class OrderRepositoryService {
    private final StandardOrderProcessor processor;
    private Order currentOrder; // Імітація БД для тесту
    
    public OrderRepositoryService(StandardOrderProcessor processor) {
        this.processor = processor;
    }

    public void save(Order order) {
        this.currentOrder = order;
    }

    public Optional<Order> findById(String id) {
        if (currentOrder != null && currentOrder.getId().equals(id)) {
            return Optional.of(currentOrder);
        }
        return Optional.empty();
    }
}
