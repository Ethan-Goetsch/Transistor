package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InputPanelFactory {

    private static final int TEXT_FIELD_WIDTH = 20;
    private static final Color GRAY_OPAQUE_COLOR = new Color(128, 128,
            128, 128);

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

        JTextField textField = new JTextField(TEXT_FIELD_WIDTH); // Setcolumn width
        textField.setForeground(GRAY_OPAQUE_COLOR); // Gray with someopacity
        textField.setText("1234AB");

        setUpFocusListener(textField);

        textFieldPanel.add(paddingPanel);
        textFieldPanel.add(textField);
        return textFieldPanel;
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