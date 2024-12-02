package util;

import model.CraftingRecipe;
import model.ItemStack;
import model.ItemRegistry;
import java.util.ArrayList;
import java.util.List;

public class RecipeLoader {
    public static List<CraftingRecipe> loadRecipes() {
        List<CraftingRecipe> recipes = new ArrayList<>();
        System.out.println("=== Loading Recipes ===");

        // Stick recipe (2 planks vertical)
        {
            ItemStack[][] grid = new ItemStack[3][3];
            grid[0][1] = new ItemStack(ItemRegistry.getItem("wooden_planks"), 1); // Top middle
            grid[1][1] = new ItemStack(ItemRegistry.getItem("wooden_planks"), 1); // Center middle
            ItemStack output = new ItemStack(ItemRegistry.getItem("stick"), 4);
            recipes.add(new CraftingRecipe(
                    "stick",
                    "Stick",
                    grid,
                    output));
            System.out.println("Added stick recipe");
        }

        // Wooden Pickaxe recipe
        {
            ItemStack[][] grid = new ItemStack[3][3];
            grid[0][0] = new ItemStack(ItemRegistry.getItem("wooden_planks"), 1);
            grid[0][1] = new ItemStack(ItemRegistry.getItem("wooden_planks"), 1);
            grid[0][2] = new ItemStack(ItemRegistry.getItem("wooden_planks"), 1);
            grid[1][1] = new ItemStack(ItemRegistry.getItem("stick"), 1);
            grid[2][1] = new ItemStack(ItemRegistry.getItem("stick"), 1);
            ItemStack output = new ItemStack(ItemRegistry.getItem("wooden_pickaxe"), 1);
            recipes.add(new CraftingRecipe("wooden_pickaxe", "Wooden Pickaxe", grid, output));
            System.out.println("Added wooden pickaxe recipe");
        }

        // Diamond Sword recipe
        {
            ItemStack[][] grid = new ItemStack[3][3];
            grid[0][1] = new ItemStack(ItemRegistry.getItem("diamond"), 1);
            grid[1][1] = new ItemStack(ItemRegistry.getItem("diamond"), 1);
            grid[2][1] = new ItemStack(ItemRegistry.getItem("stick"), 1);
            ItemStack output = new ItemStack(ItemRegistry.getItem("diamond_sword"), 1);
            recipes.add(new CraftingRecipe("diamond_sword", "Diamond Sword", grid, output));
            System.out.println("Added diamond sword recipe");
        }

        // Planks recipe
        {
            ItemStack[][] grid = new ItemStack[3][3];
            grid[0][0] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[0][1] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[0][2] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[1][0] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[1][1] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[1][2] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[2][0] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[2][1] = new ItemStack(ItemRegistry.getItem("log"), 1);
            grid[2][2] = new ItemStack(ItemRegistry.getItem("log"), 1);
            ItemStack output = new ItemStack(ItemRegistry.getItem("wooden_planks"), 9);
            recipes.add(new CraftingRecipe("wooden_planks", "Wooden Planks", grid, output));
            System.out.println("Added planks recipe");
        }

        System.out.println("Loaded " + recipes.size() + " recipes");
        return recipes;
    }
}