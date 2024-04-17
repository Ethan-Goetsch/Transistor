package ui.Menues;

import entities.RouteType;
import ui.SearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RouteTypeMenu extends JMenu {
    private SearchPanel searchPanel;
    public RouteTypeMenu(SearchPanel searchPanel){
        this.searchPanel = searchPanel;
        this.setText("Route Type");
        JMenuItem it1 = new JMenuItem("Actual route");
        JMenuItem it2 = new JMenuItem("Arial route");
        this.add(it1);
        this.add(it2);
        addActions(new JMenuItem[]{it1,it2});
    }

    private void addActions(JMenuItem[] jMenuItems) {
        jMenuItems[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchPanel.setRouteType(RouteType.ACTUAL);
            }
        });
        jMenuItems[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchPanel.setRouteType(RouteType.AERIAL);
            }
        });
    }
}
