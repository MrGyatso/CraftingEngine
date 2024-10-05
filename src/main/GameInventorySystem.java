package com.engine;

import java.util.Map;
import java.util.HashMap;

public class GameInventorySystem {
    public static void main(String[] args) {
        // Load the inventory from file or create a new one
        Inventory inventory = Inventory.loadInventory("inventory.dat");

        // Create the crafting controller
        CraftingController craftingController = new CraftingController(inventory);

        // Create the inventory view (UI)
        InventoryView view = new InventoryView(inventory, craftingController);

        // Using the reference to view explicitly
        view.updateInventoryDisplay(); // Update inventory to use it explicitly

        // Define a crafting recipe
        Map<String, Integer> requiredItems = new HashMap<>();
        requiredItems.put("Wood", 5);
        requiredItems.put("Stone", 2);
        Item craftedItem = new Item("Stone Axe", 1, "A basic stone axe");
        CraftingRecipe stoneAxeRecipe = new CraftingRecipe(requiredItems, craftedItem);

        // Attempt to craft via command line (demonstration purpose)
        System.out.println("\nAttempting to craft Stone Axe via command line:");
        craftingController.craftItem(stoneAxeRecipe);

        // Display inventory after crafting
        System.out.println("\nInventory After Crafting (command line):");
        inventory.displayInventory();

        // Add a shutdown hook to save inventory when the application closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            inventory.saveInventory("inventory.dat");
        }));
    }
}
