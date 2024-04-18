package ui.Menues;

import com.sun.tools.javac.Main;
import org.jxmapviewer.JXMapViewer;
import ui.MainWindow;
import ui.SearchPanel;

import javax.swing.*;

public class AplicationMenuBar extends JMenuBar {
    private final JXMapViewer jXMapViewer;
    private final SearchPanel searchPanel;
    private final JFrame mainWindow;
    public AplicationMenuBar( JFrame mainWindow){
        this.jXMapViewer = ((MainWindow)mainWindow).getjXMapViewer();
        this.searchPanel = ((MainWindow)mainWindow).getSearchPanel();
        this.mainWindow = mainWindow;
        initComponents();
    }

    private void initComponents(){
        Settings settingsMenu = new Settings(jXMapViewer, searchPanel);
        this.add(settingsMenu);
        JMenu frameMenu = new JMenu("Size");
        JMenuItem standardSize = new JMenuItem("Standard size");
        JMenuItem smallSize  =new JMenuItem("Small size");
        standardSize.addActionListener(e->setSizes(900, 700));
        smallSize.addActionListener(e->setSizes(720, 560));
        frameMenu.add(standardSize);
        frameMenu.add(smallSize);
        this.add(frameMenu);
    }

    private void setSizes(int mainWidth, int mainHeight){
        ((MainWindow)mainWindow).setMainWidth(mainWidth);
        ((MainWindow)mainWindow).setMainHeight(mainHeight);
        ((MainWindow)mainWindow).resize();
    }
}
