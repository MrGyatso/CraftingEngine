package view;

import javax.swing.*;
import javax.swing.border.Border; // Add this import
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.CraftingRecipe;
import controller.CraftingController;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListCellRenderer;
import model.ItemStack;

public class RecipeBookPanel extends JPanel {
    private final CraftingController controller; // This is used in the mouse listeners
    private final JList<CraftingRecipe> recipeList;
    private final DefaultListModel<CraftingRecipe> listModel;
    private boolean isShowingPreview = false;

    public RecipeBookPanel(CraftingController controller) {
        this.controller = controller;
        setBorder(null);
        setLayout(new BorderLayout());
        setOpaque(false);

        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeList.setCellRenderer(new RecipeListCellRenderer());
        recipeList.setOpaque(false);

        // Create a custom ScrollPane without borders
        JScrollPane scrollPane = new JScrollPane(recipeList) {
            @Override
            public void setBorder(Border border) {
                // Never allow a border to be set
            }
        };
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Make the scrollbar invisible
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        recipeList.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = recipeList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Rectangle bounds = recipeList.getCellBounds(index, index);
                    if (bounds != null && bounds.contains(e.getPoint())) {
                        CraftingRecipe recipe = listModel.getElementAt(index);
                        if (!isShowingPreview) {
                            controller.showRecipePreview(recipe);
                            isShowingPreview = true;
                        }
                        return;
                    }
                }
                if (isShowingPreview) {
                    controller.clearRecipePreview();
                    isShowingPreview = false;
                }
            }
        });

        recipeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (isShowingPreview) {
                    controller.clearRecipePreview();
                    isShowingPreview = false;
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private class RecipeListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            label.setOpaque(false);
            if (value instanceof CraftingRecipe recipe) {
                ItemStack output = recipe.getOutput();
                if (output != null) {
                    setIcon(output.getItem().getIcon());
                    setText(output.getItem().getName());
                }
            }
            return label;
        }
    }

    public void updateRecipes(List<CraftingRecipe> recipes) {
        listModel.clear();
        for (CraftingRecipe recipe : recipes) {
            listModel.addElement(recipe);
        }
    }
}