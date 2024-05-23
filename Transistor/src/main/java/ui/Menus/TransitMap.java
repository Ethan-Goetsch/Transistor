package ui.Menus;

import org.jxmapviewer.JXMapViewer;

import database.DatabaseManager;
import database.queries.TransitMapDataQuery;
import ui.CustomComponents.MapViewer;
import ui.MMap;
import ui.MainWindow;
import entities.Coordinate;
import entities.Path;
import entities.PathPoint;
import entities.PointType;
import entities.gtfs.GDisplayRoute;
import entities.gtfs.GShapePoint;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class TransitMap extends JMenu
{
    private final JFrame mainWindow;
    private List<Color> pathColors;
    private List<Path> displayPaths;

    public TransitMap(JFrame mainWindow)
    {
        super("Transit Map");

        this.mainWindow = mainWindow;
        JMenuItem display = new JMenuItem("Display");
        JMenuItem hide = new JMenuItem("Hide");
        display.addActionListener(e -> {
            displayAllRoutes();
        });
        hide.addActionListener(e -> {
            hideAllRoutes();
        });
        this.add(display);
        this.add(hide);
        pathColors = new ArrayList<>();
        displayPaths = new ArrayList<>();
        populatePathColors();
        populateDisplayPaths();
    }


    private void populateDisplayPaths()
    {
        List<GDisplayRoute> displayRoutes = DatabaseManager.executeAndReadQuery(new TransitMapDataQuery());

        int iter = 0;
        for (GDisplayRoute displayRoute : displayRoutes)
        {
            List<GShapePoint> displayRoutePoints = displayRoute.getShapePoints();
            if (displayRoutePoints.isEmpty())
            {
                displayRoutePoints = displayRoute.getStopPoints();
            }

            List<PathPoint> pathPoints = new ArrayList<>();

            for (GShapePoint displayRoutePoint : displayRoutePoints)
            {
                pathPoints.add(new PathPoint(new Coordinate(displayRoutePoint.getLatitude(), displayRoutePoint.getLongitude()), PointType.Normal));
            }

            displayPaths.add(new Path(pathPoints, pathColors.get(iter % pathColors.size())));
            iter++;
        }

    }

    private void displayAllRoutes()
    {
        JXMapViewer map = ((MainWindow) mainWindow).getjXMapViewer();
        ((MapViewer) map).setPaths(displayPaths);//List<Path>
        ((MapViewer) map).removeWaypoints();//List<Path>
    }
    private void hideAllRoutes() {
        JXMapViewer map = ((MainWindow) mainWindow).getjXMapViewer();
        ((MapViewer) map).setPaths(new ArrayList<>());//List<Path>
    }


    public void populatePathColors()
    {
        pathColors.add(convertToColor(0xFFFFB300)); //Vivid Yellow
        pathColors.add(convertToColor(0xFF803E75)); //Strong Purple
        pathColors.add(convertToColor(0xFFFF6800)); //Vivid Orange
        pathColors.add(convertToColor(0xFFA6BDD7)); //Very Light Blue
        pathColors.add(convertToColor(0xFFC10020)); //Vivid Red
        pathColors.add(convertToColor(0xFFCEA262)); //Grayish Yellow
        pathColors.add(convertToColor(0xFF817066)); //Medium Gray

        //The following will not be good for people with defective color vision
        pathColors.add(convertToColor(0xFF007D34)); //Vivid Green
        pathColors.add(convertToColor(0xFFF6768E)); //Strong Purplish Pink
        pathColors.add(convertToColor(0xFF00538A)); //Strong Blue
        pathColors.add(convertToColor(0xFFFF7A5C)); //Strong Yellowish Pink
        pathColors.add(convertToColor(0xFF53377A)); //Strong Violet
        pathColors.add(convertToColor(0xFFFF8E00)); //Vivid Orange Yellow
        pathColors.add(convertToColor(0xFFB32851)); //Strong Purplish Red
        pathColors.add(convertToColor(0xFFF4C800)); //Vivid Greenish Yellow
        pathColors.add(convertToColor(0xFF7F180D)); //Strong Reddish Brown
        pathColors.add(convertToColor(0xFF93AA00)); //Vivid Yellowish Green
        pathColors.add(convertToColor(0xFF593315)); //Deep Yellowish Brown
        pathColors.add(convertToColor(0xFFF13A13)); //Vivid Reddish Orange
        pathColors.add(convertToColor(0xFF232C16)); //Dark Olive Green

    }

    public static Color convertToColor(long unsignedInt)
    {
        // Mask the lower 24 bits to get the RGB values
        int rgb = (int) (unsignedInt & 0xFFFFFF);

        // Extract individual color components
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Create and return the Color object
        return new Color(red, green, blue);
    }
}
