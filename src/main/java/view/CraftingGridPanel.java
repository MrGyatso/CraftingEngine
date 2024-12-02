package view;

import javax.swing.*;
import java.awt.*;
import model.ItemStack;
import controller.CraftingController;

public class CraftingGridPanel extends JPanel {
    private final CraftingSlot[][] slots;
    private final CraftingController controller;

    public CraftingGridPanel(int rows, int cols, CraftingController controller) {
        this.controller = controller;
        setLayout(new GridLayout(rows, cols, 2, 2));
        slots = new CraftingSlot[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                slots[i][j] = new CraftingSlot(i, j, controller);
                add(slots[i][j]);
            }
        }
    }

    public void updateGrid(ItemStack[][] grid) {
        System.out.println("=== Grid Panel updating slots ===");
        for (int i = 0; i < slots.length; i++) {
            for (int j = 0; j < slots[i].length; j++) {
                if (i < grid.length && j < grid[i].length) {
                    ItemStack item = grid[i][j];
                    System.out.println("Setting item " +
                            (item != null ? item.getItem().getName() : "null") +
                            " at [" + i + "," + j + "]");
                    slots[i][j].setItemStack(item);
                } else {
                    slots[i][j].setItemStack(null);
                }
            }
        }
        revalidate();
        repaint();
    }

    public void setSlot(int row, int col, ItemStack stack) {
        if (row >= 0 && row < slots.length &&
                col >= 0 && col < slots[row].length) {
            slots[row][col].setItemStack(stack);
        }
    }

    public ItemStack getSlot(int row, int col) {
        if (row >= 0 && row < slots.length &&
                col >= 0 && col < slots[row].length) {
            return slots[row][col].getItemStack();
        } else {
            return null;
        }
    }
}
