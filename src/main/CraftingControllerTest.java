package com.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class CraftingControllerTest {

    @Test
    public void testSuccessfulCrafting() {
        Inventory inventory = new Inventory();
        inventory.addItem(new Item("Wood", 10, "Basic wood for crafting"));
        inventory.addItem(new Item("Stone", 5, "A piece of rock"));

        CraftingController controller = new CraftingController(inventory);

        Map<String, Integer> requiredItems = new HashMap<>();
        requiredItems.put("Wood", 5);
        requiredItems.put("Stone", 2);
        Item craftedItem = new Item("Stone Axe", 1, "A basic stone axe");
        CraftingRecipe stoneAxeRecipe = new CraftingRecipe(requiredItems, craftedItem);

        boolean result = controller.craftItem(stoneAxeRecipe);

        assertTrue(result);
        assertEquals(1, inventory.getItems().stream().filter(item -> item.getName().equals("Stone Axe")).count());
    }

    @Test
    public void testFailedCrafting() {
        Inventory inventory = new Inventory();
        inventory.addItem(new Item("Wood", 4, "Basic wood for crafting")); // Not enough wood
        inventory.addItem(new Item("Stone", 5, "A piece of rock"));

        CraftingController controller = new CraftingController(inventory);

        Map<String, Integer> requiredItems = new HashMap<>();
        requiredItems.put("Wood", 5);
        requiredItems.put("Stone", 2);
        Item craftedItem = new Item("Stone Axe", 1, "A basic stone axe");
        CraftingRecipe stoneAxeRecipe = new CraftingRecipe(requiredItems, craftedItem);

        boolean result = controller.craftItem(stoneAxeRecipe);

        assertFalse(result);
        assertEquals(0, inventory.getItems().stream().filter(item -> item.getName().equals("Stone Axe")).count());
    }
}
