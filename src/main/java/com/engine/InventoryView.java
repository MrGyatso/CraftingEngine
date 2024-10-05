package com.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class InventoryView extends JFrame {

    private Inventory inventory;
    private CraftingController controller;

    private DefaultListModel<String> inventoryListModel;
    private JList<String> inventoryList;
    private JPanel craftingGridPanel;
    private Item[][] craftingGrid = new Item[3][3]; // Represents the crafting grid

    public InventoryView(Inventory inventory, CraftingController controller) {
        this.inventory = inventory;
        this.controller = controller;

        setTitle("Game Inventory System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inventory List
        inventoryListModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryListModel);
        updateInventoryDisplay();
        JScrollPane scrollPane = new JScrollPane(inventoryList);
        add(scrollPane, BorderLayout.CENTER);

        // Add Item Button
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        // Craft Item Button
        JButton craftItemButton = new JButton("Craft Item");
        craftItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                craftItem();
            }
        });

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(craftItemButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize Crafting Grid
        initializeCraftingGrid();
        craftUsingGrid();

        // Make the frame visible
        setVisible(true);
    }

    // Method to update the inventory list display
    public void updateInventoryDisplay() {
        inventoryListModel.clear();
        for (Item item : inventory.getItems()) {
            inventoryListModel.addElement(item.getName() + " - Quantity: " + item.getQuantity());
        }
    }

    // Method to add an item (for demonstration purposes)
    private void addItem() {
        String[] options = { "Wood", "Stone" };
        String selectedItem = (String) JOptionPane.showInputDialog(
                this,
                "Select item to add:",
                "Add Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (selectedItem != null) {
            if (selectedItem.equals("Wood")) {
                inventory.addItem(new Item("Wood", 5, "Basic wood for crafting"));
            } else if (selectedItem.equals("Stone")) {
                inventory.addItem(new Item("Stone", 5, "A piece of rock"));
            }
            updateInventoryDisplay();
        }
    }

    // Method to craft an item (for demonstration purposes)
    private void craftItem() {
        // Sample crafting recipe
        CraftingRecipe recipe = new CraftingRecipe(Map.of("Wood", 5),
                new Item("Wooden Plank", 1, "Crafted wooden plank"));
        boolean crafted = controller.craftItem(recipe);
        if (crafted) {
            JOptionPane.showMessageDialog(this, "Crafted: Wooden Plank");
        } else {
            JOptionPane.showMessageDialog(this, "Crafting failed: insufficient items");
        }
        updateInventoryDisplay();
    }

    // Initialize the crafting grid
    private void initializeCraftingGrid() {
        craftingGridPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JLabel slotLabel = new JLabel("Empty", SwingConstants.CENTER);
                slotLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                int finalRow = row;
                int finalCol = col;
                slotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        placeItemInGrid(slotLabel, finalRow, finalCol);
                    }
                });
                craftingGridPanel.add(slotLabel);
            }
        }
        add(craftingGridPanel, BorderLayout.EAST);
    }

    private void placeItemInGrid(JLabel slotLabel, int row, int col) {
        String[] options = { "Wood", "Stone" };
        String selectedItem = (String) JOptionPane.showInputDialog(
                this,
                "Select item to place in grid:",
                "Place Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (selectedItem != null) {
            craftingGrid[row][col] = new Item(selectedItem, 1, "Placed item in crafting grid");
            slotLabel.setText(selectedItem);
        }
    }

    // Craft using the crafting grid
    private void craftUsingGrid() {
        JButton craftButton = new JButton("Craft from Grid");
        craftButton.addActionListener(e -> {
            // Check if the current grid matches any known recipe
            Item result = checkCraftingRecipe();
            if (result != null) {
                JOptionPane.showMessageDialog(this, "Crafted: " + result.getName());
                // Add the crafted item to inventory and clear the grid
                inventory.addItem(result);
                updateInventoryDisplay();
                clearCraftingGrid();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Recipe");
            }
        });
        add(craftButton, BorderLayout.NORTH);
    }

    // Check the crafting recipe based on the current grid
    private Item checkCraftingRecipe() {
        // Define a sample recipe: Example (3 stone + 1 wood = Stone Axe)
        if (craftingGrid[0][0] != null && "Stone".equals(craftingGrid[0][0].getName()) &&
                craftingGrid[1][0] != null && "Stone".equals(craftingGrid[1][0].getName()) &&
                craftingGrid[2][0] != null && "Stone".equals(craftingGrid[2][0].getName()) &&
                craftingGrid[1][1] != null && "Wood".equals(craftingGrid[1][1].getName())) {
            return new Item("Stone Axe", 1, "A crafted stone axe");
        }
        // Add other recipes here
        return null;
    }

    // Clear the crafting grid after crafting
    private void clearCraftingGrid() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                craftingGrid[row][col] = null;
            }
        }
        // Also update the UI representation
        for (Component comp : craftingGridPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setText("Empty");
            }
        }
    }
}
