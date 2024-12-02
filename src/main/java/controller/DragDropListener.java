package controller;

import model.ItemStack;

public interface DragDropListener {
    void onItemDragStarted(ItemStack item, int row, int col);

    void onItemDropped(ItemStack item, int row, int col);

    void onItemDragEnded(boolean completed);
}
