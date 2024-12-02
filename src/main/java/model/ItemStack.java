package model;

import java.awt.datatransfer.DataFlavor;
import java.io.Serializable;

public class ItemStack implements Serializable {
    private static final DataFlavor ITEM_STACK_FLAVOR = new DataFlavor(ItemStack.class, "Item Stack");

    private final Item item;
    private int count;

    public ItemStack(Item item, int count) {
        System.out.println("Creating ItemStack for " + item.getName() + " with count " + count);
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getQuantity() {
        return count;
    }

    public void removeItems(int amount) {
        count = Math.max(0, count - amount);
    }

    public ItemStack split() {
        if (count <= 1)
            return null;
        int splitAmount = count / 2;
        count -= splitAmount;
        return new ItemStack(item, splitAmount);
    }

    public ItemStack copy() {
        return new ItemStack(item, count);
    }

    public static DataFlavor getItemStackDataFlavor() {
        return ITEM_STACK_FLAVOR;
    }
}