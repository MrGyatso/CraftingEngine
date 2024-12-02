package util;

import java.awt.datatransfer.*;
import model.ItemStack;

public class ItemStackTransferable implements Transferable {
    private ItemStack itemStack;
    private static DataFlavor ITEM_STACK_FLAVOR;

    static {
        try {
            ITEM_STACK_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
                    + ";class=model.ItemStack");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ItemStackTransferable(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { ITEM_STACK_FLAVOR };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(ITEM_STACK_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(ITEM_STACK_FLAVOR)) {
            return itemStack;
        }
        throw new UnsupportedFlavorException(flavor);
    }

    public static DataFlavor getItemStackFlavor() {
        return ITEM_STACK_FLAVOR;
    }
}