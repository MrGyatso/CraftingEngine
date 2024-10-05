package com.engine;

// Model: Represents items and their characteristics
public class Item {
    private String name;
    private int quantity;
    private String extraInfo;

    public Item(String name, int quantity, String extraInfo) {
        this.name = name;
        this.quantity = quantity;
        this.extraInfo = extraInfo;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public String toString() {
        return "Item{name='" + name + '\'' + ", quantity=" + quantity + ", extraInfo='" + extraInfo + "'}";
    }
}
