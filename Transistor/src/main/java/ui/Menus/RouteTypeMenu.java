package ui.Menus;

import org.jxmapviewer.JXMapViewer;
import ui.SearchPanel;

import javax.swing.*;

public class RouteTypeMenu extends JMenu {
    private final SearchPanel searchPanel;
    private final JXMapViewer jxMapViewer;
    public RouteTypeMenu(SearchPanel searchPanel, JXMapViewer jxMapViewer){
        super("Route Type");
        this.searchPanel = searchPanel;
        this.jxMapViewer = jxMapViewer;
        initItems();
    }

    private void initItems() {
        JMenuItem it1 = new JMenuItem("Actual route");
        JMenuItem it2 = new JMenuItem("Arial route");
        this.add(it1);
        this.add(it2);
    }
}