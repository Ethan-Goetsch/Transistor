package ui.Menus;

import ui.InformationPanel;

import javax.swing.*;

public class Accessibility extends JMenu {
    public Accessibility(JPanel informationPanel)
    {
        super("accessibility");
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
        JMenu locationSensitivity = new JMenu("Sensitivity");
        JMenuItem manyLocations = new JMenuItem("Use more locations");
        JMenuItem singleLocation = new JMenuItem("Use 1 location");
        manyLocations.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeLocationNumberSensitivity(3);
        });
        singleLocation.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeLocationNumberSensitivity(1);
        });
        accessibility.add(display);
        accessibility.add(hide);
        disabledPeopleSetting.add(disabled);
        disabledPeopleSetting.add(general);
        locationSensitivity.add(manyLocations);
        locationSensitivity.add(singleLocation);
        this.add(accessibility);
        this.add(disabledPeopleSetting);
        this.add(locationSensitivity);
    }
}