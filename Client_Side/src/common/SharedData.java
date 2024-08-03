package common;

import java.util.ArrayList;
import java.util.Map;

import entities.Item;
import entities.OrderDetails;

public class SharedData {
    private static SharedData instance;
    private Object lastResponse;
    private OrderDetails orderDetails;
    private Map<String, Integer> orderMap;
    private ArrayList<Item> menuItems;
    private int totalPrice;

    private SharedData() {}

    public static synchronized SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public synchronized Object getLastResponse() {
        return lastResponse;
    }

    public synchronized void setLastResponse(Object lastResponse) {
        this.lastResponse = lastResponse;
    }

    public synchronized OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public synchronized void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    public synchronized Map<String, Integer> getOrderMap() {
        return orderMap;
    }

    public synchronized void setOrderMap(Map<String, Integer> orderMap) {
        this.orderMap = orderMap;
    }

    public synchronized ArrayList<Item> getMenuItems() {
        return menuItems;
    }

    public synchronized void setMenuItems(ArrayList<Item> menuItems) {
        this.menuItems = menuItems;
    }

    public synchronized int getTotalPrice() {
        return totalPrice;
    }

    public synchronized void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
