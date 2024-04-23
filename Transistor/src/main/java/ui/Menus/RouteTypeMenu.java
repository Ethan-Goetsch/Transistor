package ui.Menus;

import entities.RouteType;
import org.jxmapviewer.JXMapViewer;
import ui.SearchPanel;

import javax.swing.*;

public class RouteTypeMenu extends JMenu {
    private final SearchPanel searchPanel;
    public RouteTypeMenu(SearchPanel searchPanel){
        super("Route Type");
        this.searchPanel = searchPanel;
        initItems();
    }

    private void initItems() {
        JMenuItem it1 = new JMenuItem("Actual route");
        JMenuItem it2 = new JMenuItem("Arial route");
        this.add(it1);
        this.add(it2);
        addActions(new JMenuItem[]{it1,it2});
    }

    private void addActions(JMenuItem[] jMenuItems)
    {
        jMenuItems[0].addActionListener(e ->
        {
            searchPanel.setRouteType(RouteType.ACTUAL);
        });

        jMenuItems[1].addActionListener(e ->
        {
            searchPanel.setRouteType(RouteType.AERIAL);
        });
    }
}