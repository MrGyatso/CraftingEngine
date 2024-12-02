package model;

import java.util.*;
import controller.CraftingModelListener;
import util.RecipeLoader;

public class CraftingModel implements RecipeBookListener {
    private ItemStack[][] grid;
    private List<ItemStack> inventory;
    private List<CraftingModelListener> listeners;
    private List<CraftingRecipe> recipes;
    private ItemStack outputSlot;
    private RecipeBook recipeBook;

    public CraftingModel() {
        System.out.println("=== Initializing Model ===");
        grid = new ItemStack[3][3];
        inventory = new ArrayList<>();
        listeners = new ArrayList<>();
        recipes = new ArrayList<>();
        outputSlot = null;

        // Initialize RecipeBook and load recipes
        recipeBook = new RecipeBook();
        List<CraftingRecipe> loadedRecipes = RecipeLoader.loadRecipes();
        for (CraftingRecipe recipe : loadedRecipes) {
            recipeBook.addRecipe(recipe);
        }

        // Initialize inventory with good quantities of items
        inventory.add(new ItemStack(ItemRegistry.getItem("wooden_planks"), 64)); // Full stack of planks
        inventory.add(new ItemStack(ItemRegistry.getItem("stick"), 32)); // Half stack of sticks
        inventory.add(new ItemStack(ItemRegistry.getItem("diamond"), 16)); // 16 diamonds
        inventory.add(new ItemStack(ItemRegistry.getItem("log"), 32)); // Half stack of logs
        inventory.add(new ItemStack(ItemRegistry.getItem("coal"), 32)); // Half stack of coal
        inventory.add(new ItemStack(ItemRegistry.getItem("iron_ingot"), 32)); // Half stack of iron

        System.out.println("Initial inventory size: " + inventory.size());

        // Initial notification happens in constructor
        notifyInventoryUpdated();
    }

    public void setInventorySlot(int slot, ItemStack item) {
        // Ensure inventory has enough slots
        while (inventory.size() <= slot) {
            inventory.add(null);
        }

        if (slot < 0) {
            System.out.println("Invalid slot index: " + slot);
            return;
        }

        // Set the item at the specific slot
        if (slot < inventory.size()) {
            inventory.set(slot, item);
        }
        notifyInventoryUpdated();
    }

    public ItemStack getInventorySlot(int slot) {
        if (slot >= 0 && slot < inventory.size()) {
            return inventory.get(slot);
        }
        return null;
    }

    public void setItemInSlot(int row, int col, ItemStack item) {
        System.out.println("=== Model: Setting item in slot [" + row + "," + col + "] ===");
        System.out.println("Item: " + (item != null ? item.getItem().getName() : "null"));

        grid[row][col] = item;
        notifyGridUpdated(); // Use the dedicated notify method instead of direct notification

        try {
            checkRecipes();
        } catch (NullPointerException e) {
            System.out.println("Warning: Recipe checking failed - RecipeBook not initialized");
            // TODO: Initialize RecipeBook properly
        }
    }

    public ItemStack getItemInSlot(int row, int col) {
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[row].length) {
            return grid[row][col];
        }
        return null;
    }

    public void setOutputSlot(ItemStack item) {
        outputSlot = item;
        notifyOutputUpdated();
    }

    public ItemStack getOutputSlot() {
        return outputSlot;
    }

    public void checkRecipes() {
        CraftingRecipe matchingRecipe = recipeBook.findMatchingRecipe(grid);
        if (matchingRecipe != null) {
            outputSlot = matchingRecipe.getOutput().copy();
        } else {
            outputSlot = null;
        }
        notifyOutputUpdated();
    }

    public void addListener(CraftingModelListener listener) {
        System.out.println("Adding listener: " + listener.getClass().getSimpleName());
        listeners.add(listener);
    }

    private void notifyGridUpdated() {
        System.out.println("=== Model: Notifying grid updated ===");
        System.out.println("Current grid state:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                ItemStack item = grid[i][j];
                System.out.println("[" + i + "," + j + "]: " +
                        (item != null ? item.getItem().getName() : "empty"));
            }
        }

        System.out.println("Notifying " + listeners.size() + " listeners");
        for (CraftingModelListener listener : listeners) {
            System.out.println("Notifying listener: " + listener.getClass().getSimpleName());
            listener.onGridUpdated(grid);
        }
    }

    private void notifyOutputUpdated() {
        for (CraftingModelListener listener : listeners) {
            listener.onOutputUpdated(outputSlot);
        }
    }

    public void notifyInventoryUpdated() {
        System.out.println("=== Model Notifying Inventory Update ===");
        System.out.println("Listeners count: " + listeners.size());
        for (CraftingModelListener listener : listeners) {
            System.out.println("Notifying listener: " + listener.getClass().getSimpleName());
            listener.onInventoryUpdated(new ArrayList<>(inventory));
        }
    }

    public List<CraftingRecipe> getRecipes() {
        return recipes;
    }

    public void updateInventory(List<ItemStack> items) {
        System.out.println("Updating inventory with " + items.size() + " items");
        inventory.clear();
        inventory.addAll(items);
        notifyInventoryUpdated();
    }

    public void removeFromInventory(int slot) {
        if (slot >= 0 && slot < inventory.size()) {
            System.out.println("Removing item from inventory slot " + slot);
            inventory.remove(slot);
            notifyInventoryUpdated();
        }
    }

    public void addToInventory(int slot, ItemStack item) {
        System.out.println("Adding " + item.getItem().getName() + " to inventory slot " + slot);
        if (slot >= inventory.size()) {
            inventory.add(item);
        } else {
            inventory.add(slot, item);
        }
        notifyInventoryUpdated();
    }

    public int getInventorySize() {
        return inventory.size();
    }

    public int getNextEmptySlot() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == null) {
                return i;
            }
        }
        return inventory.size();
    }

    @Override
    public void onRecipeBookUpdated(List<CraftingRecipe> recipes) {
        for (CraftingModelListener listener : listeners) {
            listener.onRecipeBookUpdated(recipes);
        }
    }
}