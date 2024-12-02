package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.datatransfer.Transferable;
import model.ItemStack;
import controller.DragDropListener;
import util.ItemStackTransferable;

public class ItemTransferHandler extends TransferHandler {
    private DragDropListener listener;
    private int row;
    private int col;
    private MouseEvent lastMouseEvent;

    public ItemTransferHandler(DragDropListener listener, int row, int col) {
        this.listener = listener;
        this.row = row;
        this.col = col;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        CraftingSlot slot = (CraftingSlot) c;
        ItemStack sourceStack = slot.getItemStack();
        if (sourceStack != null) {
            // Create a new stack with just one item for left click
            if (!SwingUtilities.isRightMouseButton(lastMouseEvent)) {
                ItemStack singleItem = new ItemStack(sourceStack.getItem(), 1);
                sourceStack.removeItems(1);
                if (sourceStack.getCount() <= 0) {
                    slot.setItemStack(null);
                } else {
                    slot.setItemStack(sourceStack); // Update the UI
                }
                return new ItemStackTransferable(singleItem);
            }
            // Create a copy of the full stack for right click
            return new ItemStackTransferable(sourceStack.copy());
        }
        return null;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(ItemStackTransferable.getItemStackFlavor());
    }

    @Override
    public boolean importData(TransferSupport support) {
        try {
            Transferable transferable = support.getTransferable();
            ItemStack draggedItem = (ItemStack) transferable.getTransferData(
                    ItemStackTransferable.getItemStackFlavor());
            listener.onItemDropped(draggedItem, row, col);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    public void setLastMouseEvent(MouseEvent e) {
        this.lastMouseEvent = e;
    }
}