package ui.Menus;

import ui.MainWindow;

import javax.swing.*;

public class SizeMenu extends JMenu {
    private final JFrame mainWindow;
    public SizeMenu(JFrame mainWindow){
        super("Size");
        this.mainWindow = mainWindow;
        initItems();
    }

    private void initItems() {
        JMenuItem bigSize = new JMenuItem("Big size");
        JMenuItem standardSize = new JMenuItem("Standard size");
        JMenuItem smallSize  =new JMenuItem("Small size");
        bigSize.addActionListener(e->setSizes(1080, 840));
        standardSize.addActionListener(e->setSizes(900, 700));
        smallSize.addActionListener(e->setSizes(720, 560));
        this.add(bigSize);
        this.add(standardSize);
        this.add(smallSize);
    }

    private void setSizes(int mainWidth, int mainHeight){
        ((MainWindow)mainWindow).setMainWidth(mainWidth);
        ((MainWindow)mainWindow).setMainHeight(mainHeight);
        ((MainWindow)mainWindow).resize();
    }
}