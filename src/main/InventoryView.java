package com.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class InventoryView {
    private Inventory inventory;
    private CraftingController controller;
    private JFrame frame;
    private JTextArea inventoryArea;
    private JComboBox<String> craftingComboBox;

    // Crafting Recipes Map
    private Map<String, CraftingRecipe> craftingRecipes = new HashMap<>();

    public InventoryView(Inventory inventory, CraftingController controller) {
        this.inventory = inventory;
        this.controller = controller;

        // Set up the main frame
        frame = new JFrame("Game Inventory System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Inventory Display Area
        inventoryArea = new JTextArea();
        inventoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inventoryArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Initialize Crafting Recipes
        initializeCraftingRecipes();

        // Add Resource Buttons and Crafting Button
        JPanel buttonPanel = new JPanel();
        JButton addWoodButton = new JButton("Add Wood");
        JButton addStoneButton = new JButton("Add Stone");
        craftingComboBox = new JComboBox<>(craftingRecipes.keySet().toArray(new String[0]));
        JButton craftItemButton = new JButton("Craft Item");
        buttonPanel.add(addWoodButton);
        buttonPanel.add(addStoneButton);
        buttonPanel.add(craftingComboBox);
        buttonPanel.add(craftItemButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners for Resource Buttons
        addWoodButton.addActionListener(e -> {
            inventory.addItem(new Item("Wood", 5, "Basic wood for crafting"));
            updateInventoryDisplay();
        });

        addStoneButton.addActionListener(e -> {
            inventory.addItem(new Item("Stone", 3, "A piece of rock"));
            updateInventoryDisplay();
        });

        // Event Listener for Crafting Button
        craftItemButton.addActionListener(e -> {
            String selectedRecipeName = (String) craftingComboBox.getSelectedItem();
            if (selectedRecipeName != null && craftingRecipes.containsKey(selectedRecipeName)) {
                CraftingRecipe recipe = craftingRecipes.get(selectedRecipeName);
                boolean success = controller.craftItem(recipe);
                if (success) {
                    updateInventoryDisplay();
                } else {
                    JOptionPane.showMessageDialog(frame, "Not enough resources to craft " + selectedRecipeName,
                            "Crafting Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
        updateInventoryDisplay();
    }

    // Initialize some crafting recipes
    private void initializeCraftingRecipes() {
        // Stone Axe Recipe
        Map<String, Integer> stoneAxeRequirements = new HashMap<>();
        stoneAxeRequirements.put("Wood", 5);
        stoneAxeRequirements.put("Stone", 2);
        Item stoneAxe = new Item("Stone Axe", 1, "A basic stone axe");
        craftingRecipes.put("Stone Axe", new CraftingRecipe(stoneAxeRequirements, stoneAxe));

        // Wooden Shield Recipe
        Map<String, Integer> shieldRequirements = new HashMap<>();
        shieldRequirements.put("Wood", 8);
        Item woodenShield = new Item("Wooden Shield", 1, "A sturdy wooden shield");
        craftingRecipes.put("Wooden Shield", new CraftingRecipe(shieldRequirements, woodenShield));
    }

    // Update inventory display in the UI
    public void updateInventoryDisplay() {
        inventoryArea.setText("");
        for (Item item : inventory.getItems()) {
            inventoryArea.append(item + "\n");
        }
    }
}
