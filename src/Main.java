import models.*;
import repository.OrderRepository;
import service.OrderService;
import strategy.DefaultPricingStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Restaurant restaurant = new Restaurant("Pizza Place");

        MenuItem pizza = new MenuItem("Pizza", 200, 5);
        MenuItem burger = new MenuItem("Burger", 100, 5);

        restaurant.addItem(pizza);
        restaurant.addItem(burger);

        List<DeliveryPartner> partners = Arrays.asList(
                new DeliveryPartner(),
                new DeliveryPartner()
        );


        OrderService service = new OrderService(
                new OrderRepository(),
                partners,
                new DefaultPricingStrategy()
        );

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int userId = i;

            executor.submit(() -> {
                try {
                    User user = new User(userId, "User" + userId);

                    Cart cart = new Cart();
                    cart.addItem(pizza, 2);

                    Order order = service.placeOrder(user, cart);
                    System.out.println("Order " + order.getId() + " for User " + userId +
                            " placed. Total: " + order.calculateTotal());

                } catch (Exception e) {
                    System.out.println("Failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("All orders processed");
    }
}