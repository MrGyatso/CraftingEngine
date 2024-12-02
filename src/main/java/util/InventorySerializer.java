package util;

import model.ItemStack;
import model.ItemRegistry;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventorySerializer {
    public static void saveInventory(List<ItemStack> inventory, String filename)
            throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("inventory");
        doc.appendChild(root);

        for (ItemStack stack : inventory) {
            Element itemElem = doc.createElement("item");
            itemElem.setAttribute("id", stack.getItem().getId());
            itemElem.setAttribute("quantity", String.valueOf(stack.getQuantity()));
            root.appendChild(itemElem);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }

    public static List<ItemStack> loadInventory(String filename) throws Exception {
        List<ItemStack> inventory = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(filename));

        NodeList itemNodes = doc.getElementsByTagName("item");

        for (int i = 0; i < itemNodes.getLength(); i++) {
            Element itemElem = (Element) itemNodes.item(i);
            String itemId = itemElem.getAttribute("id");
            int quantity = Integer.parseInt(itemElem.getAttribute("quantity"));

            inventory.add(new ItemStack(ItemRegistry.getItem(itemId), quantity));
        }

        return inventory;
    }
}
