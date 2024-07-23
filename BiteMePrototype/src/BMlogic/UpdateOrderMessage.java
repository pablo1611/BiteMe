package BMlogic;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdateOrderMessage extends Message implements Serializable {
    private int orderNumber;
    private int totalPrice;
    private String orderAddress;

    public UpdateOrderMessage(String message, ArrayList<Order> orders, int orderNumber, int totalPrice, String orderAddress) {
        super(message, orders);
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.orderAddress = orderAddress;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getOrderAddress() {
        return orderAddress;
    }
}
