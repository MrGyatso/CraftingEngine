package controller;

import model.ItemStack;
import java.util.List;

public interface CraftingModelListener {
    void onGridUpdated(ItemStack[][] grid);

    void onInventoryUpdated(List<ItemStack> items);

    void onOutputUpdated(ItemStack item);

    void onError(String message);

    void onRecipeBookUpdated(List<model.CraftingRecipe> recipes);
}