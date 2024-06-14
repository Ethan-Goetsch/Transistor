package ui.Menus;

import ui.InformationPanel;

import javax.swing.*;

public class Accessibility extends JMenu {
    public Accessibility(JPanel informationPanel)
    {
        super("Accessibility");

        JMenuItem display = new JMenuItem("Display");
        JMenuItem hide = new JMenuItem("Hide");
        display.addActionListener(e -> {
            ((InformationPanel)informationPanel).showAccessibilityPanel();
        });
        hide.addActionListener(e -> {
            ((InformationPanel)informationPanel).showTripPanel();
        });
        this.add(display);
        this.add(hide);
    }
}
