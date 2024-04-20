package ui.Menues;

import ui.MainWindow;

import javax.swing.*;

public class SizeMenu extends JMenu {
    private JFrame mainWindow;
    public SizeMenu(JFrame mainWindow){
        super("Size");
        this.mainWindow = mainWindow;
        initItems();
    }

    private void initItems() {
        JMenuItem standardSize = new JMenuItem("Standard size");
        JMenuItem smallSize  =new JMenuItem("Small size");
        standardSize.addActionListener(e->setSizes(900, 700));
        smallSize.addActionListener(e->setSizes(720, 560));
        this.add(standardSize);
        this.add(smallSize);
    }

    private void setSizes(int mainWidth, int mainHeight){
        ((MainWindow)mainWindow).setMainWidth(mainWidth);
        ((MainWindow)mainWindow).setMainHeight(mainHeight);
        ((MainWindow)mainWindow).resize();
    }
}
