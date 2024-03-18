package ui;

import java.awt.*;

import javax.swing.JPanel;

public class SettingsPanel extends JPanel
{
    public SettingsPanel(int mainWidth, int mainHeight)
    {
        this.setLayout(new GridLayout(5, 2));
        this.setBackground(Color.YELLOW);
        this.setPreferredSize(new Dimension(mainWidth / 2, mainHeight));

    }
}