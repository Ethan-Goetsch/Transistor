package ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JPanel {
    InputPanelFactory inputPanelFactory;

    public SettingsPanel() {

    }

    public SettingsPanel(int mainWidth, int mainHeight) {
        this.setLayout(new GridLayout(3, 1));
        this.setPreferredSize(new Dimension(mainWidth / 3, mainHeight));
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding

        JPanel calculatePanel = createPanel();
        this.add(calculatePanel);

    }

    public JPanel createPanel() {
        JPanel calculatePanel = new JPanel();
        calculatePanel.setLayout(new GridLayout(5, 1));

        inputPanelFactory = new InputPanelFactory();
        JPanel departureInput = inputPanelFactory.createInputPanel("Departure Postal Code");
        calculatePanel.add(departureInput);

        JPanel arrivalInput = inputPanelFactory.createInputPanel("Arrival Postal Code");
        calculatePanel.add(arrivalInput);

        String[] options = { "Walking 🚶", "Biking 🚴" };
        JComboBox<String> comboBox = new JComboBox<>(options);
        calculatePanel.add(comboBox);

        JButton calculateButton = new JButton("Calculate");
        calculatePanel.add(calculateButton);

        JLabel resultPanel = new JLabel("This is where the result is: ");
        calculatePanel.add(resultPanel);

        return calculatePanel;
    }
}