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
        var route = manager.calculateRoute(request);
        if (!route.responseMessage().isEmpty())
        {
            JOptionPane.showMessageDialog(new JFrame(), route.responseMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        window.getSearchPanel().updateResults(route.departureDescription(), route.arrivalDescription());
        MMap map= window.getMap();
        map.updateResults(route.departure(), route.arrival(), route.journey().getTrips(), -1);
    }
}