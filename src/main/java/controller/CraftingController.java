package controller;

import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import model.CraftingModel;
import model.ItemStack;
import model.CraftingRecipe;
import view.CraftingWindow;

public class CraftingController implements DragDropListener, CraftingModelListener {
    private final CraftingModel model;
    private final CraftingWindow view;
    private int dragSourceRow;
    private int dragSourceCol;

    public CraftingController(CraftingModel model) {
        System.out.println("=== Initializing Controller ===");
        this.model = model;
        model.addListener(this);
        this.view = CraftingWindow.getInstance(this);
        System.out.println("Controller initialized and view created");
        this.view.show();
    }

    @Override
    public void onInventoryUpdated(List<ItemStack> items) {
        System.out.println("=== Controller Received Update ===");
        System.out.println("Items to update: " + items.size());
        SwingUtilities.invokeLater(() -> {
            System.out.println("Forwarding update to view");
            view.updateInventory(new ArrayList<>(items));
        });
    }

    @Override
    public void onGridUpdated(ItemStack[][] grid) {
        System.out.println("=== Controller received grid update ===");
        SwingUtilities.invokeLater(() -> {
            System.out.println("Forwarding grid update to view");
            view.updateGrid(grid);
        });
    }

    @Override
    public void onOutputUpdated(ItemStack output) {
        SwingUtilities.invokeLater(() -> {
            view.updateOutput(output);
        });
    }

    @Override
    public void onRecipeBookUpdated(List<CraftingRecipe> recipes) {
        SwingUtilities.invokeLater(() -> {
            view.updateRecipeBook(recipes);
        });
    }

    @Override
    public void onError(String message) {
        SwingUtilities.invokeLater(() -> {
            view.showError(message);
        });
    }

    @Override
    public void onItemDragStarted(ItemStack item, int row, int col) {
        System.out.println("Drag started: " + item.getItem().getName() + " from [" + row + "," + col + "]");
        dragSourceRow = row;
        dragSourceCol = col;
    }

    @Override
    public void onItemDropped(ItemStack item, int row, int col) {
        System.out.println("Item dropped: " + item.getItem().getName() + " at [" + row + "," + col + "]");

        // From output to inventory
        if (dragSourceRow == -2 && row == -1) { // -2 represents output slot
            ItemStack targetItem = model.getInventorySlot(col);
            if (targetItem != null && targetItem.getItem().equals(item.getItem())) {
                // Stack items if they're the same type
                targetItem.setCount(targetItem.getCount() + item.getCount());
                model.setInventorySlot(col, targetItem);
            } else if (targetItem != null) {
                // Swap items if target slot is occupied
                model.setInventorySlot(col, item);
                model.setOutputSlot(targetItem);
                return;
            } else {
                // Place in empty slot
                model.setInventorySlot(col, item);
            }

            // Clear the crafting grid after successful drag
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    model.setItemInSlot(i, j, null);
                }
            }
            model.setOutputSlot(null);
        }

        // From inventory to grid
        if (dragSourceRow == -1 && row >= 0) {
            ItemStack sourceItem = model.getInventorySlot(dragSourceCol);
            ItemStack targetItem = model.getItemInSlot(row, col);

            if (targetItem != null && targetItem.getItem().equals(item.getItem())) {
                // Stack items if they're the same type
                targetItem.setCount(targetItem.getCount() + item.getCount());
                model.setItemInSlot(row, col, targetItem);

                // Update source slot if it's empty
                if (sourceItem.getCount() <= 0) {
                    model.setInventorySlot(dragSourceCol, null);
                } else {
                    model.setInventorySlot(dragSourceCol, sourceItem);
                }
            } else if (targetItem != null) {
                // Swap items if target slot is occupied with different item
                model.setItemInSlot(row, col, item);
                model.setInventorySlot(dragSourceCol, targetItem);
            } else {
                // Place new item in empty slot
                model.setItemInSlot(row, col, item);

                // Update source slot if it's empty
                if (sourceItem.getCount() <= 0) {
                    model.setInventorySlot(dragSourceCol, null);
                } else {
                    model.setInventorySlot(dragSourceCol, sourceItem);
                }
            }
        }
        // From grid to inventory
        else if (dragSourceRow >= 0 && row == -1) {
            ItemStack targetItem = model.getInventorySlot(col);
            if (targetItem != null && targetItem.getItem().equals(item.getItem())) {
                // Stack items if they're the same type
                targetItem.setCount(targetItem.getCount() + item.getCount());
                model.setInventorySlot(col, targetItem);
                model.setItemInSlot(dragSourceRow, dragSourceCol, null);
            } else if (targetItem != null) {
                // Swap items if target slot is occupied
                model.setInventorySlot(col, item);
                model.setItemInSlot(dragSourceRow, dragSourceCol, targetItem);
            } else {
                // Place in empty slot
                model.setInventorySlot(col, item);
                model.setItemInSlot(dragSourceRow, dragSourceCol, null);
            }
        }
        // From grid to grid
        else if (dragSourceRow >= 0 && row >= 0) {
            ItemStack targetItem = model.getItemInSlot(row, col);
            if (targetItem != null && targetItem.getItem().equals(item.getItem())) {
                // Stack items if they're the same type
                targetItem.setCount(targetItem.getCount() + item.getCount());
                model.setItemInSlot(row, col, targetItem);
                model.setItemInSlot(dragSourceRow, dragSourceCol, null);
            } else if (targetItem != null) {
                // Swap items if target slot is occupied
                model.setItemInSlot(row, col, item);
                model.setItemInSlot(dragSourceRow, dragSourceCol, targetItem);
            } else {
                // Place in empty slot
                model.setItemInSlot(row, col, item);
                model.setItemInSlot(dragSourceRow, dragSourceCol, null);
            }
        }
        // From inventory to inventory
        else if (dragSourceRow == -1 && row == -1) {
            ItemStack sourceItem = model.getInventorySlot(dragSourceCol);
            ItemStack targetItem = model.getInventorySlot(col);

            if (targetItem != null && targetItem.getItem().equals(item.getItem())) {
                // Stack items if they're the same type
                targetItem.setCount(targetItem.getCount() + item.getCount());
                model.setInventorySlot(col, targetItem);
            } else if (targetItem != null) {
                // Swap items if target slot is occupied
                model.setInventorySlot(col, item);
                model.setInventorySlot(dragSourceCol, targetItem);
                return;
            } else {
                // Place in empty slot
                model.setInventorySlot(col, item);
            }

            // Update source slot if it's empty
            if (sourceItem.getCount() <= 0) {
                model.setInventorySlot(dragSourceCol, null);
            } else {
                model.setInventorySlot(dragSourceCol, sourceItem);
            }
        }

        // Reset drag source
        dragSourceRow = -1;
        dragSourceCol = -1;
    }

    @Override
    public void onItemDragEnded(boolean completed) {
        if (!completed) {
            dragSourceRow = -1;
            dragSourceCol = -1;
        }
    }

    public void onCraftButtonClicked() {
        ItemStack output = model.getOutputSlot();
        if (output != null) {
            // Add crafted item to the next empty inventory slot
            model.addToInventory(model.getNextEmptySlot(), output.copy());

            // Clear the crafting grid
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    model.setItemInSlot(i, j, null);
                }
            }
        }
    }

    public void showRecipePreview(CraftingRecipe recipe) {
        System.out.println("Showing preview for recipe: " +
                (recipe != null ? recipe.getName() : "null"));
        SwingUtilities.invokeLater(() -> {
            view.updateRecipePreview(recipe);
        });
    }

    public void clearRecipePreview() {
        System.out.println("Clearing recipe preview");
        SwingUtilities.invokeLater(() -> {
            view.updateRecipePreview(null);
        });
    }
}