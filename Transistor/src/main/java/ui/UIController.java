package ui;

import javax.swing.*;
import application.ApplicationManager;
import entities.RouteRequest;
import utils.Conversions;

public class UIController
{
    private final MainWindow window;

    private final ApplicationManager manager;

    public UIController(ApplicationManager manager)
    {
        this.manager = manager;
        this.window = new MainWindow();

        window.getRouteRequested().subscribe(this::handleRouteRequested);
    }

    private void handleRouteRequested(RouteRequest request)
    {
        var route = manager.calculateRouteRequest(request);
        if (!route.responseMessage().isEmpty())
        {
            JOptionPane.showMessageDialog(new JFrame(), route.responseMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String distance = "Distance: " + Conversions.formatDistance(route.result().distanceInKM());
        String time = "Time: " + Conversions.formatTime(route.result().timeInHours());

        window.settingsPanel.updateResults(distance, time);
//        window.mapPanel.imageHolder.plottingPanel.updateResults(route.departure(), route.arrival(), route.result().path());
        //TODO change the ploting
    }
}