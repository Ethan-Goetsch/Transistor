package ui.Menus;

import org.jxmapviewer.JXMapViewer;
import ui.SearchPanel;

import javax.swing.*;

public class Settings extends JMenu {
    private final JXMapViewer jXMapViewer;
    private final SearchPanel searchPanel;
    public Settings(JXMapViewer jXMapViewer, SearchPanel searchPanel){
        super("Settings");
        this.jXMapViewer = jXMapViewer;
        this.searchPanel = searchPanel;
        initItems();
    }

    private void initItems() {
        JMenuItem mapTypeSelect = new MapType(jXMapViewer);
        this.add(mapTypeSelect);
        JMenuItem routeTypeSelection = new RouteTypeMenu(searchPanel,jXMapViewer);
        this.add(routeTypeSelection);
    }
}