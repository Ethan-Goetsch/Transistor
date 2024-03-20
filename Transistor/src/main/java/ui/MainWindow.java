package ui;

import entities.RouteRequest;
import utils.IObservable;
import utils.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

    private LayoutManager layout = new BorderLayout();
    private final int mainWidth = 1200;
    private final int mainHeight = 961;

    SettingsPanel settingsPanel;

    MapPanel mapPanel;

    public SettingsPanel getSettingsPanel() {
        return this.settingsPanel;
    }

    private final Subject<RouteRequest> onRouteRequested;

    public MainWindow() {
        this.onRouteRequested = new Subject<>();

        configureWindow();
        initializeElements();
        this.setVisible(true);
    }

    public IObservable<RouteRequest> getRouteRequested() {
        return onRouteRequested;
    }

    public void configureWindow() {
        this.setSize(mainWidth, mainHeight);
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(layout);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeElements() {
        System.out.println("test" + getWidth());

        mapPanel = new MapPanel(mainWidth, mainHeight);
        this.add(mapPanel, BorderLayout.CENTER);

        settingsPanel = new SettingsPanel(mainWidth, mainHeight, onRouteRequested::execute);
        this.add(settingsPanel, BorderLayout.EAST);
    }
}
