package entities;

public class QuarterlyReport extends Report {
    private int orderDistribution;
    private float quarterlyRevenue;

    // Getters and Setters
    public int getOrderDistribution() {
        return orderDistribution;
    }

    public void setOrderDistribution(int orderDistribution) {
        this.orderDistribution = orderDistribution;
    }

    public float getQuarterlyRevenue() {
        return quarterlyRevenue;
    }

    public void setQuarterlyRevenue(float quarterlyRevenue) {
        this.quarterlyRevenue = quarterlyRevenue;
    }
}
