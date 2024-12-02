package view;

import javax.swing.*;
import java.awt.*;
import model.ItemStack;
import controller.CraftingController;

public class GridPanel extends JPanel {
    private final CraftingSlot[][] slots;
    private final CraftingController controller;

    public GridPanel(int rows, int cols, CraftingController controller) {
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
        for (int i = 0; i < slots.length; i++) {
            for (int j = 0; j < slots[i].length; j++) {
                if (i < grid.length && j < grid[i].length) {
                    slots[i][j].setItemStack(grid[i][j]);
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
        }
        return null;
    }
}