package ui;

import entities.RouteRequest;
import entities.TransportType;
import utils.IAction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SettingsPanel extends JPanel {
    private final IAction<RouteRequest> onCalculateClicked;

    private JTextField departureField;
    private JTextField arrivalField;
    JPanel resultPanel;
    JLabel distanceLabel;
    JLabel timeLabel;

    public SettingsPanel(int mainWidth, int mainHeight, IAction<RouteRequest> onCalculateCLicked) {
        this.setLayout(new GridLayout(3, 1));
        this.setPreferredSize(new Dimension(mainWidth / 3, mainHeight));
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding

        JPanel calculatePanel = createPanel();
        this.add(calculatePanel);
        this.onCalculateClicked = onCalculateCLicked;
    }

    public JPanel createPanel() {
        departureField = createTextField();
        arrivalField = createTextField();

        JPanel calculatePanel = new JPanel();
        calculatePanel.setLayout(new GridLayout(5, 1));

        JPanel departureInput = createInputPanel("Departure Postal Code", departureField);
        calculatePanel.add(departureInput);

        JPanel arrivalInput = createInputPanel("Arrival Postal Code", arrivalField);
        calculatePanel.add(arrivalInput);

        // Change to enums for later
        JComboBox<TransportType> comboBox = new JComboBox<>(TransportType.values());
        calculatePanel.add(comboBox);

        JButton calculateButton = new JButton("Calculate");
        calculatePanel.add(calculateButton);

        calculateButton.addActionListener(e -> onCalculateClicked.execute(new RouteRequest(departureField.getText(),
                arrivalField.getText(), (TransportType) comboBox.getSelectedItem())));

        resultPanel = new JPanel();
        distanceLabel = new JLabel();
        timeLabel = new JLabel();
        resultPanel.add(distanceLabel);
        resultPanel.add(timeLabel);
        // calculatePanel.add(resultPanel);

        return calculatePanel;
    }

    public void updateResults(String distance, String time) {
        distanceLabel.setText(distance);
        timeLabel.setText(time);
    }

    private static final int TEXT_FIELD_WIDTH = 20;
    private static final Color GRAY_OPAQUE_COLOR = new Color(128, 128,
            128, 128);

    public JPanel createInputPanel(String postalCodeString, JTextField textField) {
        JPanel inputPanel = new JPanel();
        JLabel label = new JLabel(postalCodeString);
        JPanel textFieldPanel = createTextFieldPanel(textField);

        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.add(label);
        inputPanel.add(textFieldPanel);
        return inputPanel;
    }

    // Helper method to create textfieldPanel
    public JPanel createTextFieldPanel(JTextField textField) {
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new GridLayout(3, 1));
        JPanel paddingPanel = new JPanel();

        setUpFocusListener(textField);

        textFieldPanel.add(paddingPanel);
        textFieldPanel.add(textField);
        return textFieldPanel;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(TEXT_FIELD_WIDTH);
        textField.setForeground(GRAY_OPAQUE_COLOR); // Gray with someopacity
        textField.setText("1234AB");
        return textField;
    }

    // Helper method to set up the focus listener for the text field
    private void setUpFocusListener(JTextField textField) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("1234AB")) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK); // Change tonormal color immediately
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(GRAY_OPAQUE_COLOR); // Graywith opacity
                    textField.setText("1234AB");
                } else {
                    textField.setForeground(Color.BLACK); // Or yourdesired normal color
                }
            }
        });
    }
}