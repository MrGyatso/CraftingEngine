package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import model.ItemStack;
import controller.CraftingController;

public class InventoryPanel extends JPanel {
    private static final Color MINECRAFT_WINDOW_GRAY = new Color(0xC6C6C6);
    private static final int SLOT_SIZE = 32;
    private static final int SLOT_SPACING = 2;
    private final List<InventorySlot> slots;
    private final CraftingController controller;

    public InventoryPanel(CraftingController controller) {
        this.controller = controller;
        this.slots = new ArrayList<>();

        setLayout(new BorderLayout(0, 8)); // 8 pixel gap between inventory and hotbar
        setBackground(MINECRAFT_WINDOW_GRAY);

        // Main inventory panel (3x9)
        JPanel mainInventory = new JPanel(new GridLayout(3, 9, SLOT_SPACING, SLOT_SPACING));
        mainInventory.setBackground(MINECRAFT_WINDOW_GRAY);

        // Force size of main inventory slots
        Dimension slotDim = new Dimension(SLOT_SIZE, SLOT_SIZE);
        for (int i = 0; i < 27; i++) {
            InventorySlot slot = new InventorySlot(i, controller);
            slot.setPreferredSize(slotDim);
            slots.add(slot);
            mainInventory.add(slot);
        }

        // Hotbar (1x9)
        JPanel hotbar = new JPanel(new GridLayout(1, 9, SLOT_SPACING, SLOT_SPACING));
        hotbar.setBackground(MINECRAFT_WINDOW_GRAY);

        // Force size of hotbar slots
        for (int i = 27; i < 36; i++) {
            InventorySlot slot = new InventorySlot(i, controller);
            slot.setPreferredSize(slotDim);
            slots.add(slot);
            hotbar.add(slot);
        }

        // Add components with proper spacing
        add(mainInventory, BorderLayout.CENTER);
        add(hotbar, BorderLayout.SOUTH);

        // Set preferred size for entire panel
        int totalWidth = 9 * SLOT_SIZE + 8 * SLOT_SPACING + 4; // 9 slots + 8 gaps + padding
        int totalHeight = 4 * SLOT_SIZE + 3 * SLOT_SPACING + 12; // 4 rows + 3 gaps + padding + gap between inv/hotbar
        setPreferredSize(new Dimension(totalWidth, totalHeight));
    }

    public void updateInventory(List<ItemStack> items) {
        System.out.println("=== Panel Updating Slots ===");
        System.out.println("Total slots: " + slots.size());
        System.out.println("Items to place: " + items.size());

        // Clear all slots first
        for (InventorySlot slot : slots) {
            slot.setItemStack(null);
        }

        // Set new items
        for (int i = 0; i < items.size() && i < slots.size(); i++) {
            ItemStack item = items.get(i);
            if (item != null) {
                System.out.println("Setting item " + item.getItem().getName() + " in slot " + i);
                slots.get(i).setItemStack(item);
            }
        }

        revalidate();
        repaint();
    }

    public void addItem(ItemStack item) {
        for (InventorySlot slot : slots) {
            if (slot.getItemStack() == null) {
                slot.setItemStack(item);
                break;
            }
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < slots.size()) {
            slots.get(index).setItemStack(null);
        }
    }
}