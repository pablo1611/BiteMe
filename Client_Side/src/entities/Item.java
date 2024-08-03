package entities;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String restaurant;
    private String type;
    private String name;
    private int price;
    private String note1;
    private String note2;
    private String note3;

    // Constructor
    public Item(String restaurant, String type, String name, int price, String note1, String note2, String note3) {
        this.restaurant = restaurant;
        this.type = type;
        this.name = name;
        this.price = price;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
    }

    // Getters
    public String getRestaurant() {
        return restaurant;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getNote1() {
        return note1;
    }

    public String getNote2() {
        return note2;
    }

    public String getNote3() {
        return note3;
    }
}
