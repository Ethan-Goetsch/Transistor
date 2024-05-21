package ui.Menus;

import org.jxmapviewer.JXMapViewer;
import ui.CustomComponents.MapViewer;
import ui.MMap;
import ui.MainWindow;

import javax.swing.*;

public class TransitMap extends JMenu {
    private final JFrame mainWindow;
    private boolean displayed;

    public TransitMap(JFrame mainWindow) {
        super("Transit Map");
        this.mainWindow = mainWindow;
        displayed = false;
        this.addActionListener(e -> {displayAllRoutes();});
    }

    private void displayAllRoutes(){
        JXMapViewer map = ((MainWindow)mainWindow).getjXMapViewer();
        if(!displayed){

//        ((MapViewer) map).setPaths(paths);//List<Path>
        }else{
            ((MapViewer) map).setPaths(null);//List<Path>
        }

    }
}
