package ui;

import java.awt.*;

import javax.swing.*;

public class MapPanel extends JPanel {
        public MapPanel(int mainWidth, int mainHeight) {
                this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
                this.setLayout(new BorderLayout());
                JLayeredPane imageHolder = new ImageHolder();
                imageHolder.setSize(mainWidth / 2, mainHeight);
                this.add(imageHolder, BorderLayout.CENTER);
        }

}