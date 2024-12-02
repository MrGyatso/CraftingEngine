package view;

import controller.DragDropListener;

public class OutputSlot extends CraftingSlot {
    public OutputSlot(DragDropListener listener) {
        super(-2, 0, listener); // -2 indicates output slot
    }
}