package ui.Menus;

import ui.InformationPanel;

import javax.swing.*;

public class Accessibility extends JMenu {
    public Accessibility(JPanel informationPanel)
    {
        super("Accessibility");
        JMenu accessibility = new JMenu("Set display");
        JMenuItem display = new JMenuItem("Show");
        JMenuItem hide = new JMenuItem("Hide");
        display.addActionListener(e -> {
            ((InformationPanel)informationPanel).showAccessibilityPanel();
        });
        hide.addActionListener(e -> {
            ((InformationPanel)informationPanel).showTripPanel();
        });
        JMenu disabledPeopleSetting = new JMenu("Disabled person");
        JMenuItem disabled = new JMenuItem("Use accessibility for disabled");
        JMenuItem general = new JMenuItem("Use general accessibility");
        disabled.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeDisabledPersonSetting(true);
        });
        general.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeDisabledPersonSetting(false);
        });
        accessibility.add(display);
        accessibility.add(hide);
        disabledPeopleSetting.add(disabled);
        disabledPeopleSetting.add(general);
        this.add(accessibility);
        this.add(disabledPeopleSetting);
    }
}