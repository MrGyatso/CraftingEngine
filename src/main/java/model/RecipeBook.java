package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeBook {
    private final Map<String, CraftingRecipe> recipes;
    private final List<RecipeBookListener> listeners;

    public RecipeBook() {
        this.recipes = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void addRecipe(CraftingRecipe recipe) {
        if (recipe != null && recipe.getId() != null) {
            recipes.put(recipe.getId(), recipe);
            notifyRecipeBookUpdated();
        }
    }

    public void removeRecipe(String id) {
        if (id != null && recipes.containsKey(id)) {
            recipes.remove(id);
            notifyRecipeBookUpdated();
        }
    }

    public CraftingRecipe getRecipe(String id) {
        return recipes.get(id);
    }

    public List<CraftingRecipe> getAllRecipes() {
        return new ArrayList<>(recipes.values());
    }

    public CraftingRecipe findMatchingRecipe(ItemStack[][] grid) {
        for (CraftingRecipe recipe : recipes.values()) {
            if (recipe.matches(grid)) {
                return recipe;
            }
        }
        return null;
    }

    private void notifyRecipeBookUpdated() {
        List<CraftingRecipe> recipeList = getAllRecipes();
        for (RecipeBookListener listener : listeners) {
            listener.onRecipeBookUpdated(recipeList);
        }
    }

    public void addListener(RecipeBookListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(RecipeBookListener listener) {
        listeners.remove(listener);
    }
}