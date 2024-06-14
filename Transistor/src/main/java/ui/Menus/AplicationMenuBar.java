package ui.Menus;

import ui.MainWindow;
import ui.Panels.InputRoutingSearchPanel;
import javax.swing.*;

public class AplicationMenuBar extends JMenuBar {
    private final InputRoutingSearchPanel inputRoutingSearchPanel;
    private final JFrame mainWindow;
    public AplicationMenuBar( JFrame mainWindow){
        this.inputRoutingSearchPanel = ((MainWindow)mainWindow).getInputRoutingSearchPanel();
        this.mainWindow = mainWindow;
        initComponents();
    }
    private void initComponents(){
        Settings settingsMenu = new Settings(((MainWindow)mainWindow).getjXMapViewer(), inputRoutingSearchPanel);
        this.add(settingsMenu);
        this.add( new SizeMenu(mainWindow));
        this.add(new TransitMap(mainWindow));
        this.add(new Accessibility(((MainWindow)mainWindow).getInformationPanel()));
    }


}