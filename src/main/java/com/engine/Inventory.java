package com.engine;

import java.util.ArrayList;
import java.util.List;
import java.io.*; // Added for Serialization

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L; // Required for serialization
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    // Add an item to the inventory
    public void addItem(Item item) {
        for (Item i : items) {
            if (i.getName().equals(item.getName())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    // Remove an item from the inventory
    public boolean removeItem(String itemName, int quantity) {
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                if (i.getQuantity() >= quantity) {
                    i.setQuantity(i.getQuantity() - quantity);
                    if (i.getQuantity() == 0) {
                        items.remove(i);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // Display inventory items
    public void displayInventory() {
        for (Item i : items) {
            System.out.println(i);
        }
    }

    // Getter for items
    public List<Item> getItems() {
        return items;
    }

    // Save inventory to a file
    public void saveInventory(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Load inventory from a file
    public static Inventory loadInventory(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Inventory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
            return new Inventory(); // Return an empty inventory if load fails
        }
    }
}
