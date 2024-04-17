package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class CircularIconButton extends JRadioButton {

    public CircularIconButton(ImageIcon imageIcon) {
        super(imageIcon);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

}
