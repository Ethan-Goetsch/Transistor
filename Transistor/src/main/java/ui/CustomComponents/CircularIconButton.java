package ui.CustomComponents;

import javax.swing.*;

public class CircularIconButton extends JRadioButton {

    public CircularIconButton(ImageIcon imageIcon) {
        //iconCircular.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)
        super(imageIcon);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

}
