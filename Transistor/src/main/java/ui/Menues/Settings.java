package ui.Menues;

import org.jxmapviewer.JXMapViewer;
import ui.SearchPanel;

import javax.swing.*;

public class Settings extends JMenu {
    private JXMapViewer jXMapViewer;
    private SearchPanel searchPanel;
    public Settings(JXMapViewer jXMapViewer, SearchPanel searchPanel){
        super("Settings");
        this.jXMapViewer = jXMapViewer;
        this.searchPanel = searchPanel;
        initComponents();
    }

    private void initComponents() {
        JMenuItem mapTypeSelect = new MapType(jXMapViewer);
        this.add(mapTypeSelect);
        JMenuItem routeTypeSelection = new RouteTypeMenu(searchPanel,jXMapViewer);
        this.add(routeTypeSelection);
    }
}
