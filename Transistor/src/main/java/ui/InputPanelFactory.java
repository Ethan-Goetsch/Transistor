package ui;

import javax.swing.*;
import java.awt.*;

public class InputPanelFactory {

    public JPanel createInputPanel(String postalCodeString) {
        JPanel inputPanel = new JPanel();
        JLabel label = new JLabel(postalCodeString);
        JPanel textFieldPanel = createTextFieldPanel();

        inputPanel.setLayout(new GridLayout(1, 2));
        inputPanel.add(label);
        inputPanel.add(textFieldPanel);
        return inputPanel;
    }

    // Helper method to create textfieldPanel
    public JPanel createTextFieldPanel() {
        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new GridLayout(3, 1));
        JPanel paddingPanel = new JPanel();
        JTextField textField = new JTextField();
        textFieldPanel.add(paddingPanel);
        textFieldPanel.add(textField);
        return textFieldPanel;
    }

}
