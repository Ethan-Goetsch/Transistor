package ui;

import javax.swing.*;
import application.ApplicationManager;
import entities.RouteRequest;
import utils.Conversions;

public class UIController {
    private final MainWindow window;

    private final ApplicationManager manager;

    public UIController(ApplicationManager manager)
    {
        this.manager = manager;
        this.window = new MainWindow();

        window.getRouteRequested().subscribe(this::handleRouteRequested);
    }

    private void handleRouteRequested(RouteRequest request) {

        if(request == null){
            JOptionPane.showMessageDialog(new JFrame(), "Not valid route!", "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var route = manager.calculateRouteRequest(request);

        if (!route.responseMessage().isEmpty())
        {
            JOptionPane.showMessageDialog(new JFrame(), route.responseMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        window.settingsPanel.updateResults(String.valueOf((int) route.distance()), Conversions.timeDivision(route.time()));
        window.mapPanel.imageHolder.plottingPanel.updateResults(route.departure(), route.arrival());
    }
}