package ui;

import entities.RouteRequest;
import entities.TransportType;
import utils.IAction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel
{
    private InputPanelFactory inputPanelFactory;
    private final IAction<RouteRequest> onCalculateClicked;

    public SettingsPanel(int mainWidth, int mainHeight, IAction<RouteRequest> onCalculateCLicked) {
        this.setLayout(new GridLayout(3, 1));
        this.setPreferredSize(new Dimension(mainWidth / 3, mainHeight));
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding

        JPanel calculatePanel = createPanel();
        this.add(calculatePanel);
        this.onCalculateClicked = onCalculateCLicked;
    }

    public JPanel createPanel() {
        JPanel calculatePanel = new JPanel();
        calculatePanel.setLayout(new GridLayout(5, 1));

        inputPanelFactory = new InputPanelFactory();
        JPanel departureInput = inputPanelFactory.createInputPanel("Departure Postal Code");
        calculatePanel.add(departureInput);

        JPanel arrivalInput = inputPanelFactory.createInputPanel("Arrival Postal Code");
        calculatePanel.add(arrivalInput);

        // Change to enums for later
        String[] options = { "Walking 🚶", "Biking 🚴" };
        JComboBox<String> comboBox = new JComboBox<>(options);
        calculatePanel.add(comboBox);

        JButton calculateButton = new JButton("Calculate");
        calculatePanel.add(calculateButton);

        calculateButton.addActionListener(e -> onCalculateClicked.execute(new RouteRequest("", "", TransportType.WALKING)));

        JLabel resultPanel = new JLabel("This is where the result is: ");
        calculatePanel.add(resultPanel);

        return calculatePanel;
    }
}