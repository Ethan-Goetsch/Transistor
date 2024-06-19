package ui;

import ui.Panels.AccessibilityPanel;
import ui.Panels.TripPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InformationPanel extends JPanel {
    private TripPanel tripPanel;
    private AccessibilityPanel accessibilityPanel;
    private int mainWidth;
    private int mainHeight;
    private CardLayout cardLayout;

    public InformationPanel(JPanel tripPanel, JPanel accessibilityPanel, int mainWidth, int mainHeight) {
        this.tripPanel = (TripPanel) tripPanel;
        this.accessibilityPanel = (AccessibilityPanel) accessibilityPanel;
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;

        this.setBackground(Color.white);
        // Adjust or remove the border as needed
        this.setBorder(new EmptyBorder(0, 0, 0, 0)); // Set to 0 padding for no gap
        changeSize(mainWidth, mainHeight);
        setLayout();
    }

    private void setLayout() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        this.add(tripPanel, "TripPanel");
        this.add(accessibilityPanel, "AccessibilityPanel");
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth / 3, mainHeight));
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.tripPanel.changeSize(mainWidth, mainHeight);
        this.accessibilityPanel.changeSize(mainWidth, mainHeight);
    }

    public void showTripPanel() {
        cardLayout.show(this, "TripPanel");
    }
    public void showAccessibilityPanel() {
        cardLayout.show(this, "AccessibilityPanel");
    }

    public AccessibilityPanel getAccessibilityPanel() {
        return accessibilityPanel;
    }
}
