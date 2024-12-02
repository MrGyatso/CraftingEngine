package view;

import controller.DragDropListener;

public class InventorySlot extends CraftingSlot {
    private int slotNumber;

    public InventorySlot(int slotNumber, DragDropListener listener) {
        super(-1, slotNumber, listener);
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}