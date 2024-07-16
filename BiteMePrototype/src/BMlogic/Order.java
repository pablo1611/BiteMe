package BMlogic;

public class Order {

    private String restaurantName;
    private int orderNumber;
    private int totalPrice;
    private int orderListNum;
    private String orderAddress;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderList() {
        return orderListNum;
    }

    public void setOrderList(int orderListNum) {
        this.orderListNum = orderListNum;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
    @Override
    public String toString() {
        return "Order Details: | Restaurant: " + restaurantName + 
               " | Order Number: " + orderNumber + 
               " | Total Price: " + totalPrice + 
               " | Order List Number: " + orderListNum + 
               " | Order Address: " + orderAddress;
    }
    
}
