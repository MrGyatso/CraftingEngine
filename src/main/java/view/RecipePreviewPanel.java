package view;

import javax.swing.*;
import java.awt.*;
import model.ItemStack;

public class RecipePreviewPanel extends JPanel {
    private final JLabel outputSlot;
    private static final int SLOT_SIZE = 32;

    public RecipePreviewPanel() {
        setLayout(null); // Use absolute positioning
        setBorder(BorderFactory.createTitledBorder("Recipe Preview"));

        // Just create the output slot
        outputSlot = new JLabel();
        outputSlot.setBounds(4, 20, SLOT_SIZE, SLOT_SIZE);
        outputSlot.setOpaque(true);
        outputSlot.setBackground(Color.WHITE);
        outputSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(outputSlot);
    }

    public void showRecipe(ItemStack[][] grid, ItemStack output) {
        // Only update output
        if (output != null) {
            outputSlot.setIcon(output.getItem().getIcon());
            outputSlot.setToolTipText(output.getItem().getName());
        } else {
            outputSlot.setIcon(null);
            outputSlot.setToolTipText(null);
        }
        revalidate();
        repaint();
    }

    public void clear() {
        outputSlot.setIcon(null);
        outputSlot.setToolTipText(null);
        revalidate();
        repaint();
    }
}