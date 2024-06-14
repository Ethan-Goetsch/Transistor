package ui;

import javax.swing.*;
import application.ApplicationManager;
import entities.AccessibilityRequest;
import entities.Request;
import entities.RouteRequest;
import entities.Trip;
import org.locationtech.jts.triangulate.tri.Tri;

import java.util.ArrayList;
import java.util.List;

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
        if(request instanceof RouteRequest){
            var route = manager.calculateRouteRequest((RouteRequest) request);
            if (!route.responseMessage().isEmpty())
            {
                JOptionPane.showMessageDialog(new JFrame(), route.responseMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
                return;
            }

            window.getInputRoutingSearchPanel().updateResults(route.departureDescription(), route.arrivalDescription());
            window.getTripPanel().getTripInformationPanel().updateResults(route);
            MMap map= window.getMap();
            map.updateResults(route.departure(), route.arrival(), route.journey().getTrips(), -1);

        }else if(request instanceof AccessibilityRequest){

            var accessibilityMeasure = manager.getAccessibilityMeasure((AccessibilityRequest) request);
            if (accessibilityMeasure == null)
            {
                JOptionPane.showMessageDialog(new JFrame(), accessibilityMeasure.message(), "Error Message", JOptionPane.ERROR_MESSAGE);
                return;
            }
            window.getOutputAccessibilityPanel().updateResults(accessibilityMeasure.indexes());
            MMap map= window.getMap();
            map.updateResults(accessibilityMeasure.postalCodeLocation(), accessibilityMeasure.postalCodeLocation(), new ArrayList<>(), -1);
        }

    }
}