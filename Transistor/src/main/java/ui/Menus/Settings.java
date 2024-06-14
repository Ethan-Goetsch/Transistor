package ui.Menus;

import org.jxmapviewer.JXMapViewer;
import ui.Panels.InputRoutingSearchPanel;

import javax.swing.*;

public class Settings extends JMenu {
    private final JXMapViewer jXMapViewer;
    private final InputRoutingSearchPanel inputRoutingSearchPanel;
    public Settings(JXMapViewer jXMapViewer, InputRoutingSearchPanel inputRoutingSearchPanel){
        super("Settings");
        this.jXMapViewer = jXMapViewer;
        this.inputRoutingSearchPanel = inputRoutingSearchPanel;
        initItems();
    }

    private void initItems() {
        JMenuItem mapTypeSelect = new MapType(jXMapViewer);
        this.add(mapTypeSelect);
        JMenuItem routeTypeSelection = new RouteTypeMenu(inputRoutingSearchPanel);
        this.add(routeTypeSelection);
    }
}