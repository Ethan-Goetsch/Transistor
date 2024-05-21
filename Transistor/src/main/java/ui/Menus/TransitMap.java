package ui.Menus;

import org.jxmapviewer.JXMapViewer;
import ui.CustomComponents.MapViewer;
import ui.MMap;
import ui.MainWindow;

import javax.swing.*;

public class TransitMap extends JMenu {
    private final JFrame mainWindow;

    public TransitMap(JFrame mainWindow) {
        super("Transit Map");
        this.mainWindow = mainWindow;
    }

    private void displayAllRoutes(){
        JXMapViewer map = ((MainWindow)mainWindow).getjXMapViewer();
//        ((MapViewer) map).setPaths(paths);//List<Path>
    }
}
