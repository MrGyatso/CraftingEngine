package com.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class CraftingControllerTest {

    @Test
    public void testSuccessfulCrafting() {
        // Setup inventory and controller
        Inventory inventory = new Inventory();
        inventory.addItem(new Item("Wood", 10, "Basic wood for crafting"));
        inventory.addItem(new Item("Stone", 5, "A piece of rock"));

        // Define a recipe
        CraftingRecipe stoneAxeRecipe = new CraftingRecipe(Map.of(
                "Wood", 5,
                "Stone", 2), new Item("Stone Axe", 1, "A basic stone axe"));

        // Create crafting controller
        CraftingController controller = new CraftingController(inventory);

        // Attempt to craft the item
        boolean crafted = controller.craftItem(stoneAxeRecipe);

        // Assertions to verify the behavior
        assertTrue(crafted, "Crafting should succeed with valid items in inventory");

        // Verify the item was added to the inventory
        assertNotNull(inventory.getItems().stream()
                .filter(item -> item.getName().equals("Stone Axe"))
                .findFirst()
                .orElse(null), "Stone Axe should be present in the inventory after crafting");

        // Verify the correct quantity of items after crafting
        assertEquals(5, inventory.getItems().stream()
                .filter(item -> item.getName().equals("Wood"))
                .findFirst()
                .get()
                .getQuantity(), "5 units of Wood should remain after crafting");
    }

    @Test
    public void testFailedCrafting() {
        // Setup inventory and controller
        Inventory inventory = new Inventory();
        inventory.addItem(new Item("Wood", 2, "Basic wood for crafting"));

        // Define a recipe requiring more items than available
        CraftingRecipe stoneAxeRecipe = new CraftingRecipe(Map.of(
                "Wood", 5,
                "Stone", 2), new Item("Stone Axe", 1, "A basic stone axe"));

        // Create crafting controller
        CraftingController controller = new CraftingController(inventory);

        // Attempt to craft the item
        boolean crafted = controller.craftItem(stoneAxeRecipe);

        // Assertions to verify the behavior
        assertFalse(crafted, "Crafting should fail due to insufficient items in inventory");
    }
}