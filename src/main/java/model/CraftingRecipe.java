package model;

public class CraftingRecipe {
    private final String id;
    private final String name;
    private final ItemStack[][] grid;
    private final ItemStack output;

    public CraftingRecipe(String id, String name, ItemStack[][] grid, ItemStack output) {
        this.id = id;
        this.name = name;
        this.grid = deepCopyGrid(grid);
        this.output = output;
    }

    private ItemStack[][] deepCopyGrid(ItemStack[][] original) {
        ItemStack[][] copy = new ItemStack[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (original[i][j] != null) {
                    copy[i][j] = original[i][j].copy();
                }
            }
        }
        return copy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemStack[][] getGrid() {
        return deepCopyGrid(grid);
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    public boolean matches(ItemStack[][] otherGrid) {
        if (otherGrid == null)
            return false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ItemStack recipeItem = grid[i][j];
                ItemStack otherItem = otherGrid[i][j];

                if (recipeItem == null && otherItem == null)
                    continue;
                if (recipeItem == null || otherItem == null)
                    return false;

                if (!recipeItem.getItem().getName().equals(otherItem.getItem().getName())) {
                    return false;
                }

                if (otherItem.getCount() < recipeItem.getCount()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CraftingRecipe that = (CraftingRecipe) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}