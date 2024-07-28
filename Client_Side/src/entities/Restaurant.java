package entities;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;
	private String branch;
    private int restaurantId;

    // Getters and Setters
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
