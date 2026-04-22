package models;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DeliveryPartner {

    private static final AtomicInteger counter = new AtomicInteger(1);

    private final int id;
    private final AtomicBoolean available = new AtomicBoolean(true);

    public DeliveryPartner() {
        this.id = counter.getAndIncrement();
    }

    public boolean tryAssign() {
        return available.compareAndSet(true, false);
    }

    public void markAvailable() {
        available.set(true);
    }

    public boolean isAvailable() {
        return available.get();
    }

    public int getId() {
        return this.id;
    }
}