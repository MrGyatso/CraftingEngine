package view;

import controller.CraftingController;
import model.ItemStack;
import model.CraftingRecipe;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CraftingWindow {
    private static final int WINDOW_WIDTH = 370; // Increased width more
    private static final int WINDOW_HEIGHT = 350; // Increased height more
    private static final int SLOT_SIZE = 32;
    private static final Color MINECRAFT_SLOT_GRAY = new Color(0x8B8B8B);
    private static final Color MINECRAFT_WINDOW_GRAY = new Color(0xC6C6C6);

    private JFrame frame;
    private GridPanel gridPanel;
    private OutputSlotPanel outputPanel;
    private InventoryPanel inventoryPanel;
    private RecipePreviewPanel recipePreviewPanel;
    private RecipeBookPanel recipeBookPanel;
    private final CraftingController controller;

    private CraftingWindow(CraftingController controller) {
        this.controller = controller;
        setupWindow();
    }

    private void setupWindow() {
        frame = new JFrame("Minecraft Crafting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.getContentPane().setBackground(MINECRAFT_WINDOW_GRAY);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(MINECRAFT_WINDOW_GRAY);
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Initialize panels
        gridPanel = new GridPanel(3, 3, controller);
        outputPanel = new OutputSlotPanel(controller);
        inventoryPanel = new InventoryPanel(controller);

        // Crafting grid slightly to the left
        int gridX = (WINDOW_WIDTH - (SLOT_SIZE * 3 + 4)) / 2 - 40; // Moved more left
        gridPanel.setBounds(gridX, 8, SLOT_SIZE * 3 + 4, SLOT_SIZE * 3 + 4);
        gridPanel.setBackground(MINECRAFT_SLOT_GRAY);

        // Output slot - positioned relative to grid
        outputPanel.setOpaque(false);
        outputPanel.setBounds(gridPanel.getX() + gridPanel.getWidth() + 44,
                gridPanel.getY() + SLOT_SIZE,
                SLOT_SIZE, SLOT_SIZE);

        // Larger arrow
        JLabel arrowLabel = new JLabel("\u2192"); // Unicode right arrow
        arrowLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Larger arrow
        arrowLabel.setBounds(gridPanel.getX() + gridPanel.getWidth() + 12,
                outputPanel.getY(),
                28, // Wider bounds for larger arrow
                SLOT_SIZE);
        arrowLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Inventory positioning with more space on right and bottom
        inventoryPanel.setBounds(8,
                gridPanel.getY() + gridPanel.getHeight() + 30,
                WINDOW_WIDTH - 24, // More right side space
                SLOT_SIZE * 4 + 32); // More bottom space

        // Add components
        mainPanel.add(gridPanel);
        mainPanel.add(outputPanel);
        mainPanel.add(arrowLabel);
        mainPanel.add(inventoryPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void updateGrid(ItemStack[][] grid) {
        SwingUtilities.invokeLater(() -> {
            gridPanel.updateGrid(grid);
            frame.revalidate();
            frame.repaint();
        });
    }

    public void updateInventory(List<ItemStack> items) {
        SwingUtilities.invokeLater(() -> {
            inventoryPanel.updateInventory(items);
            frame.revalidate();
            frame.repaint();
        });
    }

    public void updateOutput(ItemStack item) {
        outputPanel.updateOutput(item);
    }

    public void updateRecipeBook(List<CraftingRecipe> recipes) {
        recipeBookPanel.updateRecipes(recipes);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showRecipePreview(ItemStack[][] input, ItemStack result) {
        if (recipePreviewPanel != null) {
            recipePreviewPanel.showRecipe(input, result);
        }
    }

    public void updateRecipePreview(CraftingRecipe recipe) {
        if (recipe != null) {
            recipePreviewPanel.showRecipe(recipe.getGrid(), recipe.getOutput());
        } else {
            recipePreviewPanel.clear();
        }
        frame.revalidate();
        frame.repaint();
    }

    private static CraftingWindow instance;

    public static CraftingWindow getInstance(CraftingController controller) {
        if (instance == null) {
            instance = new CraftingWindow(controller);
        }
        return instance;
    }
}