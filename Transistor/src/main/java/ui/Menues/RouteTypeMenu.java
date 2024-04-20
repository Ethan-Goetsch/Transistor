package ui.Menues;

import entities.RouteType;
import org.jxmapviewer.JXMapViewer;
import ui.CustomComponents.MapViewer;
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
        addActions(new JMenuItem[]{it1,it2});
    }

    private void addActions(JMenuItem[] jMenuItems) {
        jMenuItems[0].addActionListener(e -> {
            searchPanel.setRouteType(RouteType.ACTUAL);
            ((MapViewer)jxMapViewer).changeRouteType(RouteType.ACTUAL);
        });
        jMenuItems[1].addActionListener(e -> {
//                searchPanel.setRouteType(RouteType.AERIAL); //TODO ask Ethan about the way the actual route and areal route is calculated. Currently we use Actual route in both, and just paint what we want
            ((MapViewer)jxMapViewer).changeRouteType(RouteType.AERIAL);
        });
    }
}
