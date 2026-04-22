package repository;

import models.Order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository {
    private Map<Integer, Order> orders = new ConcurrentHashMap<>();

    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    public Order get(int id) {
        return orders.get(id);
    }
}
