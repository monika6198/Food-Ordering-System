package service;

import enums.OrderStatus;
import models.*;
import repository.OrderRepository;
import strategy.PricingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderService {
    private OrderRepository repo;
    private List<DeliveryPartner> partners;
    private PricingStrategy pricingStrategy;
    private AtomicInteger orderId = new AtomicInteger(1);

    public OrderService(OrderRepository repo,
                        List<DeliveryPartner> partners,
                        PricingStrategy pricingStrategy) {
        this.repo = repo;
        this.partners = partners;
        this.pricingStrategy = pricingStrategy;
    }

    public Order placeOrder(User user, Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();

        // 🔒 Reserve inventory + snapshot
        for (Map.Entry<MenuItem, Integer> entry : cart.getItems().entrySet()) {

            MenuItem menuItem = entry.getKey();
            int qty = entry.getValue();

            boolean success = menuItem.reserve(qty);
            if (!success) {
                throw new RuntimeException("Out of stock: " + menuItem.getName());
            }

            orderItems.add(new OrderItem(
                    menuItem.getName(),
                    menuItem.getPrice(),
                    qty
            ));
        }

        Order order = new Order(orderId.getAndIncrement(), user, orderItems);

        assignDelivery(order);

        repo.save(order);

        return order;
    }

    private void assignDelivery(Order order) {
        for (DeliveryPartner p : partners) {
            if (p.tryAssign()) {
                order.assignPartner(p);
                order.setStatus(OrderStatus.ACCEPTED);
                return;
            }
        }
        throw new RuntimeException("No delivery partner available");
    }

    public void updateStatus(Order order, OrderStatus status) {
        order.setStatus(status);
    }

}
