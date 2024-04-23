package ui.Menus;

import ui.MainWindow;
import ui.SearchPanel;
import javax.swing.*;

public class AplicationMenuBar extends JMenuBar {
    private final SearchPanel searchPanel;
    private final JFrame mainWindow;
    public AplicationMenuBar( JFrame mainWindow){
        this.searchPanel = ((MainWindow)mainWindow).getSearchPanel();
        this.mainWindow = mainWindow;
        initComponents();
    }
    private void initComponents(){
        Settings settingsMenu = new Settings(((MainWindow)mainWindow).getjXMapViewer(), searchPanel);
        this.add(settingsMenu);
        this.add( new SizeMenu(mainWindow));
    }


}