package model;

import java.util.List;

public interface RecipeBookListener {
    void onRecipeBookUpdated(List<CraftingRecipe> recipes);
}