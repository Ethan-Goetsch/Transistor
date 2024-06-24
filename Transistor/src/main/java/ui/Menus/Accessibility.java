package ui.Menus;

import entities.TransportType;
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
        JMenu transportType = new JMenu("Transport considered");
        JMenuItem walkingTransportType = new JMenuItem("Walking");
        JMenuItem bikeTransportType = new JMenuItem("Bike");
        JMenuItem busTransportType = new JMenuItem("Bus");
        walkingTransportType.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeTransportType(TransportType.FOOT);
        });
        bikeTransportType.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeTransportType(TransportType.BIKE);
        });
        busTransportType.addActionListener(e -> {
            ((InformationPanel)informationPanel).getAccessibilityPanel().getInputPanel().changeTransportType(TransportType.BUS);
        });
        accessibility.add(display);
        accessibility.add(hide);
        disabledPeopleSetting.add(disabled);
        disabledPeopleSetting.add(general);
        locationSensitivity.add(manyLocations);
        locationSensitivity.add(singleLocation);
        transportType.add(walkingTransportType);
        transportType.add(bikeTransportType);
        transportType.add(busTransportType);
        this.add(accessibility);
        this.add(disabledPeopleSetting);
        this.add(locationSensitivity);
        this.add(transportType);
    }
}