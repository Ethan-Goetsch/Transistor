package ui;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class UI {
    public static void uitest() {
        Window();
    }

    public static void Window() {
        JFrame mainFrame = new JFrame("Aerial Distance Calculator");

        mainFrame.setSize(1400, 700);
        int mainWidth = mainFrame.getWidth();
        int mainHeight = mainFrame.getHeight();
        mainFrame.setLayout(new BorderLayout());

        MapPanel mapPanel = new MapPanel(mainWidth, mainHeight);
        mainFrame.add(mapPanel, BorderLayout.WEST);

        SettingsPanel settingsPanel = new SettingsPanel(mainWidth, mainHeight);
        mainFrame.add(settingsPanel, BorderLayout.EAST);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
