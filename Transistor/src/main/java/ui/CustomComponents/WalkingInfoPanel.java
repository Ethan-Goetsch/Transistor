package ui.CustomComponents;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class WalkingInfoPanel extends JPanel implements Resizible {
    public WalkingInfoPanel(int mainWidth, int mainHeight) {
        changeSize(mainWidth, mainHeight);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension(mainWidth / 3, mainHeight / 5));
    }
}
