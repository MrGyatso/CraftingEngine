package view;

import javax.swing.*;
import java.awt.*;
import model.ItemStack;
import controller.CraftingController;

public class OutputSlotPanel extends JPanel {
    private OutputSlot outputSlot;
    private static final int SLOT_SIZE = 32;
    private static final Color MINECRAFT_SLOT_GRAY = new Color(0x8B8B8B);

    public OutputSlotPanel(CraftingController controller) {
        setLayout(null);

        outputSlot = new OutputSlot(controller);
        outputSlot.setBounds(0, 0, SLOT_SIZE, SLOT_SIZE);
        outputSlot.setBackground(MINECRAFT_SLOT_GRAY);

        add(outputSlot);
        setPreferredSize(new Dimension(SLOT_SIZE, SLOT_SIZE));
        setOpaque(false);
    }

    public void updateOutput(ItemStack item) {
        outputSlot.setItemStack(item);
    }
}