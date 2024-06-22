package ui;

import javax.swing.*;
import application.ApplicationManager;
import entities.AccessibilityRequest;
import entities.Request;
import entities.RouteRequest;
import java.util.ArrayList;
public class UIController
{
    private final MainWindow window;

    private final ApplicationManager manager;

    public UIController(ApplicationManager manager)
    {
        this.manager = manager;
        this.window = new MainWindow();

        window.getRequest().subscribe(this::handleRequested);
    }

    private void handleRequested(Request request)
    {
        if(request instanceof RouteRequest)
        {
            var route = manager.processRouteRequest((RouteRequest) request);
            if (!route.responseMessage().isEmpty())
            {
                showInvalidInputMessage(route.responseMessage());
                return;
            }

            window.getInputRoutingSearchPanel().updateResults(route.departureDescription(), route.arrivalDescription());
            window.getTripPanel().getTripInformationPanel().updateResults(route);
            MMap map = window.getMap();
            map.updateResults(route.departure(), route.arrival(), route.journey().getTrips(), -1);

        }
        else if (request instanceof AccessibilityRequest)
        {
            var accessibilityMeasure = manager.calculateAccessibilityMeasure((AccessibilityRequest) request);
            if (!accessibilityMeasure.message().isEmpty())
            {
                showInvalidInputMessage(accessibilityMeasure.message());
                return;
            }

            window.getOutputAccessibilityPanel().updateResults(accessibilityMeasure.indexes());
            MMap map= window.getMap();
            map.updateResults(accessibilityMeasure.postalCodeLocation(), accessibilityMeasure.postalCodeLocation(), new ArrayList<>(), -1);
        }
    }

    private static void showInvalidInputMessage(String accessibilityMeasure)
    {
        JOptionPane.showMessageDialog(new JFrame(), accessibilityMeasure, "Error Message", JOptionPane.ERROR_MESSAGE);
        return;
    }
}