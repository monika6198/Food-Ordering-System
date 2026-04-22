package strategy;

import models.OrderItem;

import java.util.List;

public interface PricingStrategy {
    double calculate(List<OrderItem> items);
}
