package entities;

public class Menu {
    private String itemName;
    private float itemPrice;
    private String itemCategory;
    private String customizationOption;

    // Getters and Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getCustomizationOption() {
        return customizationOption;
    }

    public void setCustomizationOption(String customizationOption) {
        this.customizationOption = customizationOption;
    }
}

