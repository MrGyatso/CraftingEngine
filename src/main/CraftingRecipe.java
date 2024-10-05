package com.engine;

import java.util.Map;

// Crafting Recipe class
public class CraftingRecipe {
    private Map<String, Integer> requiredItems;
    private Item resultItem;

    public CraftingRecipe(Map<String, Integer> requiredItems, Item resultItem) {
        this.requiredItems = requiredItems;
        this.resultItem = resultItem;
    }

    public Map<String, Integer> getRequiredItems() {
        return requiredItems;
    }

    public Item getResultItem() {
        return resultItem;
    }
}
