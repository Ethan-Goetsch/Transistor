package ui;

import application.ApplicationManager;
import entities.RouteRequest;

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
    }
}