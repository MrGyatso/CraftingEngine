package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.ItemStack;
import controller.DragDropListener;

public class CraftingSlot extends JPanel {
    private static final Color SLOT_BACKGROUND = new Color(0x8B8B8B);
    private static final Color SHADOW_BORDER = new Color(0x373737);
    private static final Color HIGHLIGHT_BORDER = new Color(0xFFFFFF);

    private final int row;
    private final int col;
    private final DragDropListener listener;
    private ItemStack itemStack;

    public CraftingSlot(int row, int col, DragDropListener listener) {
        this.row = row;
        this.col = col;
        this.listener = listener;

        setPreferredSize(new Dimension(32, 32));
        setOpaque(true);
        setBackground(SLOT_BACKGROUND);
        setBorder(null); // Remove default border to use custom painting

        enableDragAndDrop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Draw the darker borders (top and left)
        g.setColor(SHADOW_BORDER);
        g.drawLine(0, 0, width - 1, 0); // Top border
        g.drawLine(0, 0, 0, height - 1); // Left border

        // Draw the lighter borders (bottom and right)
        g.setColor(HIGHLIGHT_BORDER);
        g.drawLine(1, height - 1, width - 1, height - 1); // Bottom border
        g.drawLine(width - 1, 1, width - 1, height - 1); // Right border

        // If there's an item stack, paint its icon
        if (itemStack != null && itemStack.getItem() != null && itemStack.getItem().getIcon() != null) {
            Icon icon = itemStack.getItem().getIcon();
            int x = (width - icon.getIconWidth()) / 2;
            int y = (height - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g, x, y);
        }
    }

    private void enableDragAndDrop() {
        setTransferHandler(new ItemTransferHandler(listener, row, col));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (itemStack != null) {
                    JComponent c = (JComponent) e.getSource();
                    ItemTransferHandler handler = (ItemTransferHandler) c.getTransferHandler();
                    handler.setLastMouseEvent(e);

                    if (listener != null) {
                        listener.onItemDragStarted(itemStack, row, col);
                    }

                    handler.exportAsDrag(c, e, TransferHandler.MOVE);
                }
            }
        });
    }

    public void setItemStack(ItemStack stack) {
        this.itemStack = stack;
        if (stack != null) {
            setToolTipText(stack.getItem().getName() + " (x" + stack.getCount() + ")");
        } else {
            setToolTipText(null);
        }
        repaint();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}