package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class ScrollBarCustom extends JScrollBar {
    public ScrollBarCustom(){
        setUI(new ScrollBarUI());
        setPreferredSize(new Dimension(9,8));
        setForeground(new Color(35, 98, 223, 128));
        setBackground(Color.WHITE);
    }
}
