package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputTextField extends JTextField {
    private static final Color GRAY_OPAQUE_COLOR = new Color(220, 255, 255, 128);
    private final String defaultText;
    public InputTextField(String text){
        super(text);
        this.defaultText = text;
        this.setBackground(Color.white);
        this.setSize(new Dimension(20,30));
        this.setBorder(new RoundedBorder(25, GRAY_OPAQUE_COLOR));
        setUpFocusListener();
    }

    private void setUpFocusListener() {
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(defaultText)) {
                    setText("");
                    setForeground(Color.BLACK); // Change tonormal color immediately
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(GRAY_OPAQUE_COLOR); // Graywith opacity
                    setText(defaultText);
                } else {
                    setForeground(Color.BLACK); // Or yourdesired normal color
                }
            }
        });
    }
    public String getDefaultText(){
        return defaultText;
    }
}
