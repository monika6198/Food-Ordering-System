package models;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<MenuItem, Integer> items = new HashMap<>();

    public void addItem(MenuItem item, int qty) {
        items.put(item, items.getOrDefault(item, 0) + qty);
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }
}
