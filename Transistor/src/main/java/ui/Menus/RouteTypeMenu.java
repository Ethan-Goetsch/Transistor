package ui.Menus;

import entities.RouteType;
import ui.Panels.InputRoutingSearchPanel;

import javax.swing.*;

public class RouteTypeMenu extends JMenu {
    private final InputRoutingSearchPanel inputRoutingSearchPanel;
    public RouteTypeMenu(InputRoutingSearchPanel inputRoutingSearchPanel){
        super("Route Type");
        this.inputRoutingSearchPanel = inputRoutingSearchPanel;
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
            inputRoutingSearchPanel.setRouteType(RouteType.ACTUAL);
        });

        jMenuItems[1].addActionListener(e ->
        {
            inputRoutingSearchPanel.setRouteType(RouteType.AERIAL);
        });
    }
}