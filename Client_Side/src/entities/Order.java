package entities;

import java.time.LocalDate;

public class Order {
    private LocalDate orderDate;
    private String restaurant;
    private String branch;
    private int price;

    public Order(LocalDate orderDate, String restaurant, String branch, int price) {
        this.orderDate = orderDate;
        this.restaurant = restaurant;
        this.branch = branch;
        this.price = price;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getBranch() {
        return branch;
    }

    public int getPrice() {
        return price;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
