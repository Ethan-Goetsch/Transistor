package ui;

import java.awt.*;

import javax.swing.JPanel;

public class MapPanel extends JPanel
{
        public MapPanel(int mainWidth, int mainHeight)
        {
                this.setBackground(Color.BLUE);
                this.setPreferredSize(new Dimension(mainWidth / 2, mainHeight));
                JPanel imagePanel = new ImagePanel();
                imagePanel.setSize(mainWidth / 2, mainHeight);
                this.add(imagePanel);
        }
}