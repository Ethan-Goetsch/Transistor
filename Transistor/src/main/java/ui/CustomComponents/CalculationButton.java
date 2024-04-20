package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class CalculationButton extends JButton {
    public CalculationButton(String text){
        super(text);
        this.setBackground(Color.white);
        this.setBorder(new RoundedBorder(25, new Color(35, 98, 223, 128)));
        this.setFocusPainted(false);
    }
}
