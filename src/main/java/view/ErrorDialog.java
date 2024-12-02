package view;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog {
    private static JDialog currentDialog;

    public static void show(String message, Component parent) {
        if (currentDialog != null && currentDialog.isVisible()) {
            currentDialog.dispose();
        }

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Error");
        currentDialog = dialog;

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}