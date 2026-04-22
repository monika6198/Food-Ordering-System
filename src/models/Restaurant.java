package models;

import java.util.HashMap;
import java.util.Map;

public class Restaurant {
    private String name;
    private Map<String, MenuItem> menu = new HashMap<>();

    public Restaurant(String name) {
        this.name = name;
    }

    public void addItem(MenuItem item) {
        menu.put(item.getName(), item);
    }

    public MenuItem getItem(String name) {
        return menu.get(name);
    }
}
