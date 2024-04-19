package ui.Menues;

import entities.RouteType;
import org.jxmapviewer.JXMapViewer;
import ui.CustomComponents.MapViewer;
import ui.SearchPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RouteTypeMenu extends JMenu {
    private SearchPanel searchPanel;
    private JXMapViewer jxMapViewer;
    public RouteTypeMenu(SearchPanel searchPanel, JXMapViewer jxMapViewer){
        this.searchPanel = searchPanel;
        this.jxMapViewer = jxMapViewer;
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
                ((MapViewer)jxMapViewer).changeRouteType(RouteType.ACTUAL);
            }
        });
        jMenuItems[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                searchPanel.setRouteType(RouteType.AERIAL); //TODO ask Ethan about the way the actual route and areal route is calculated. Currently we use Actual route in both, and just paint what we want
                ((MapViewer)jxMapViewer).changeRouteType(RouteType.AERIAL);
            }
        });
    }
}
