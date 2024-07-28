package entities;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int customerId;
    private String restaurant;
    private String orderType;
    private String phoneNumber;
    private String address;

    public OrderDetails(int customerId, String restaurant, String orderType, String phoneNumber, String address) {
        this.customerId = customerId;
        this.restaurant = restaurant;
        this.orderType = orderType;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
