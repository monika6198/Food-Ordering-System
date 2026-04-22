package strategy;

import models.OrderItem;

import java.util.List;

public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculate(List<OrderItem> items) {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
