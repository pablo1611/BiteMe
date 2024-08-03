package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private String orderType;
    private String restaurant;
    private String branch;
    private String address;
    private int numberOfClients;
    private String deliveryTime;

    public OrderDetails(User user, String orderType) {
        this.user = user;
        this.orderType = orderType;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String time) {
        this.deliveryTime = time;
    }
    
    @Override
    public String toString() {
        return "OrderDetails{" +
                "user=" + user +
                ", orderType='" + orderType + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", branch='" + branch + '\'' +
                ", address='" + address + '\'' +
                ", numberOfParticipants=" + numberOfClients +
                ", deliveryTime='" + deliveryTime + '\'' +
                '}';
    }
}
