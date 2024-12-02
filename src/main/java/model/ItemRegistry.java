package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ItemRegistry {
    private static Map<String, Item> items = new HashMap<>();
    private static ImageIcon defaultIcon;

    public static void initialize() {
        System.out.println("Initializing ItemRegistry...");

        // Load default icon first
        defaultIcon = loadIcon("assets/items/default.png");
        if (defaultIcon == null) {
            System.err.println("Failed to load default icon!");
            return;
        }

        // Register items with specific icons
        registerItem(new Item("wooden_planks", "Wooden Planks", loadIcon("assets/items/wooden_planks.png")));
        registerItem(new Item("stick", "Stick", loadIcon("assets/items/stick.png")));
        registerItem(new Item("diamond", "Diamond", loadIcon("assets/items/diamond.png")));
        registerItem(new Item("iron_ingot", "Iron Ingot", loadIcon("assets/items/iron_ingot.png")));
        registerItem(new Item("wooden_pickaxe", "Wooden Pickaxe", loadIcon("assets/items/wooden_pickaxe.png")));
        registerItem(new Item("diamond_sword", "Diamond Sword", loadIcon("assets/items/diamond_sword.png")));
        registerItem(new Item("log", "Log", loadIcon("assets/items/log.png")));
        registerItem(new Item("coal", "Coal", loadIcon("assets/items/coal.png")));

        System.out.println("Registered items:");
        for (String itemId : items.keySet()) {
            System.out.println("- " + itemId + ": " + items.get(itemId).getName());
        }
    }

    public static void registerItem(Item item) {
        System.out.println("Registering item: " + item.getId());
        items.put(item.getId(), item);
    }

    public static Item getItem(String id) {
        Item item = items.get(id);
        System.out.println("Getting item " + id + ": " + (item != null ? "found" : "null"));
        return item;
    }

    private static ImageIcon loadIcon(String path) {
        try {
            System.out.println("Attempting to load image from: " + new File(path).getAbsolutePath());
            BufferedImage img = ImageIO.read(new File(path));
            if (img != null) {
                System.out.println("Original image dimensions: " + img.getWidth() + "x" + img.getHeight());
                Image scaled = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
                System.out.println("Scaled icon dimensions: 28x28");
                return new ImageIcon(scaled);
            }
        } catch (IOException e) {
            System.out.println("Error loading icon from " + path + ": " + e.getMessage());
        }
        return defaultIcon;
    }
}