package models;

import java.util.concurrent.locks.ReentrantLock;

public class MenuItem {
    private String name;
    private double price;
    private int quantity;

    private ReentrantLock lock = new ReentrantLock();

    public MenuItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // 🔒 Thread-safe inventory check + update
    public boolean reserve(int qty) {
        lock.lock();
        try {
            if (quantity >= qty) {
                quantity -= qty;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
