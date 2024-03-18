package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {
    LayoutManager layout = new BorderLayout();
    final int mainWidth = 1200;
    final int mainHeight = 961;

    public MainWindow() {
        configureWindow();
        initializeElements();
        this.setVisible(true);
    }

    public void configureWindow() {
        this.setSize(mainWidth, mainHeight);
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(layout);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeElements() {
        System.out.println("test" + getWidth());

        MapPanel mapPanel = new MapPanel(mainWidth, mainHeight);
        this.add(mapPanel, BorderLayout.CENTER);

        SettingsPanel settingsPanel = new SettingsPanel(mainWidth, mainHeight);
        this.add(settingsPanel, BorderLayout.EAST);

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle mouse click within the JPanel
                System.out.println("X: " + e.getX() + ", Y: " + e.getY());
            }
        });
    }
}
