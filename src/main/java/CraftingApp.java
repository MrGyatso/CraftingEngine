import controller.CraftingController;
import model.CraftingModel;
import model.ItemRegistry;
import javax.swing.SwingUtilities;

public class CraftingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("=== Starting Application ===");
            ItemRegistry.initialize();

            CraftingModel model = new CraftingModel();
            System.out.println("Model created");

            CraftingController controller = new CraftingController(model);
            System.out.println("Controller created");

            // Force an update after everything is initialized
            model.notifyInventoryUpdated();
            System.out.println("Initial update triggered");
        });
    }
}
