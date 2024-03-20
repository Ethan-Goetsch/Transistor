package ui;

import java.awt.*;

import javax.swing.*;

public class MapPanel extends JPanel {
        ImageHolder imageHolder;
        public MapPanel(int mainWidth, int mainHeight) {
                this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
                this.setLayout(new BorderLayout());
                imageHolder = new ImageHolder();
                imageHolder.setSize(mainWidth / 2, mainHeight);
                this.add(imageHolder, BorderLayout.CENTER);
        }

}