package com.engine;

import java.util.Map;

// Controller: Handles crafting logic
public class CraftingController {
    private Inventory inventory;

    public CraftingController(Inventory inventory) {
        this.inventory = inventory;
    }

    // Attempt to craft an item
    public boolean craftItem(CraftingRecipe recipe) {
        // Check if all required items are available in sufficient quantities
        for (Map.Entry<String, Integer> entry : recipe.getRequiredItems().entrySet()) {
            String itemName = entry.getKey();
            int requiredQuantity = entry.getValue();

            boolean itemAvailable = false;
            for (Item i : inventory.getItems()) {
                if (i.getName().equals(itemName) && i.getQuantity() >= requiredQuantity) {
                    itemAvailable = true;
                    break;
                }
            }

            if (!itemAvailable) {
                System.out.println("Cannot craft. Missing item: " + itemName);
                return false;
            }
        }

        // Remove required items from inventory
        for (Map.Entry<String, Integer> entry : recipe.getRequiredItems().entrySet()) {
            inventory.removeItem(entry.getKey(), entry.getValue());
        }

        // Add crafted item to inventory
        inventory.addItem(recipe.getResultItem());
        System.out.println("Crafted: " + recipe.getResultItem().getName());
        return true;
    }
}
