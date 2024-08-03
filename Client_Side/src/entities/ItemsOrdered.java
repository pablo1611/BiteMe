package entities;

import java.io.Serializable;

public class ItemsOrdered implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderId;
    private String restaurant;
    private String branch;
    private String item;
    private String type;
    private int amount;
    private int price;

    public ItemsOrdered(int orderId, String restaurant, String branch, String item, String type, int amount, int price) {
        this.orderId = orderId;
        this.restaurant = restaurant;
        this.branch = branch;
        this.item = item;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getBranch() {
        return branch;
    }

    public String getItem() {
        return item;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }
}

