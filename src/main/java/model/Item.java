package model;

import javax.swing.ImageIcon;

public class Item {
    private final String id;
    private final String name;
    private final ImageIcon icon;

    public Item(String id, String name, ImageIcon icon) {
        System.out.println("Creating item " + id + " with icon: " + (icon != null ? "present" : "null"));
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        System.out.println("Getting icon for " + name + ": " + (icon != null ? "present" : "null"));
        return icon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}