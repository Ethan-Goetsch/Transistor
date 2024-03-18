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

        Toolkit tk = Toolkit.getDefaultToolkit();
        int mainWidth = ((int) tk.getScreenSize().getWidth()); // Set size of the mainFrame to the screen size
        int mainHeight = ((int) tk.getScreenSize().getHeight());
        mainFrame.setSize(mainWidth, mainHeight);

        JPanel mapPanel = new JPanel();
        mainFrame.add(mapPanel, BorderLayout.WEST);
        mapPanel.setBackground(Color.BLUE);
        mapPanel.setPreferredSize(new Dimension(mainWidth / 2, mainHeight));

        JTextField test = new JTextField("Hello");

        mapPanel.add(test);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
