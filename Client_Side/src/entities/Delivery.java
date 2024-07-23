package entities;

public class Delivery {
    private int deliveryId;
    private String deliveryStatus;
    private String deliveryTime;
    private String address;
    private float deliveryBasePrice;
    private String deliveryType;

    // Getters and Setters
    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDeliveryBasePrice() {
        return deliveryBasePrice;
    }

    public void setDeliveryBasePrice(float deliveryBasePrice) {
        this.deliveryBasePrice = deliveryBasePrice;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
