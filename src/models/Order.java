package models;

import enums.OrderStatus;

import java.util.List;

public class Order {
    private int id;

    private User user;
    //we are not storing cart here because cart is mutable but order is not
    //private Cart cart; // seems logical but is incorrect
    List<OrderItem> items;
    private OrderStatus status;
    private DeliveryPartner partner;

    public Order(int id, User user, List<OrderItem> items) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.status = OrderStatus.CREATED;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void assignPartner(DeliveryPartner partner) {
        this.partner = partner;
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public int getId() {
        return id;
    }
}
