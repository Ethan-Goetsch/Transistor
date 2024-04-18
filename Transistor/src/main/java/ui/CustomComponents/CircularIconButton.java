package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class CircularIconButton extends JRadioButton {

    public CircularIconButton(ImageIcon imageIcon) {
        //iconCircular.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        super(imageIcon);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

}
