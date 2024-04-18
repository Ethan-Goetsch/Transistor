package ui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.MouseInputListener;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.shapes.GHPoint3D;
import entities.Coordinate;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.*;
import ui.CustomComponents.MapViewer;
import ui.CustomComponents.Waypoint;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MMap extends JPanel{
    private final Set<Waypoint> waypoints = new HashSet<>();
    private final double LAT_CENTER = 50.8471966;
    private final double LON_CENTER = 5.7015544;
    private final int mainWidth;
    private final int mainHeight;
    private JXMapViewer jXMapViewer;


    public MMap(JXMapViewer jXMapViewer, int mainWidth, int mainHeight) {
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
        this.jXMapViewer = jXMapViewer;
        initComponents();
        initMap();
    }
    private void initMap() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition position = new GeoPosition(LAT_CENTER, LON_CENTER);
        jXMapViewer.setAddressLocation(position);
        jXMapViewer.setZoom(5);

        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
    }

    private void initWaypoints(){
        WaypointPainter<Waypoint> wp = new WaypointPainter<>();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (Waypoint point: waypoints) {
            jXMapViewer.add(point.getRepresentation());
        }
    }

    private void clearWaypoint(){
        for (Waypoint point: waypoints) {
            jXMapViewer.remove(point.getRepresentation());
        }
        waypoints.clear();
        initWaypoints();
    }

    private void addWaypoint(GeoPosition gp, ImageIcon image){
        waypoints.add(new Waypoint(gp, image));
        initWaypoints();
    }

    private void initComponents() {

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jXMapViewer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jXMapViewer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
                jXMapViewerLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jXMapViewerLayout.createSequentialGroup()
                                .addContainerGap(mainWidth, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
                jXMapViewerLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jXMapViewerLayout.createSequentialGroup()
                                .addGap(0, mainHeight, Short.MAX_VALUE))
        );
    }


    private boolean firstPoint = true;
    ResponsePath path;
    public void updateResults(Coordinate departure, Coordinate arrival, ResponsePath bestPath){
        //TODO remove existing waypoints
        ((MapViewer)jXMapViewer).bestPath(bestPath);
        double departureLong = departure.getLongitude();
        double departureLat = departure.getLatitude();
        double arrivalLong = arrival.getLongitude();
        double arrivalLat = arrival.getLatitude();

        addWaypoint(new GeoPosition(arrivalLat, arrivalLong), new ImageIcon("Transistor/src/main/resources/locationIcon.png"));//add Arrival
        addWaypoint(new GeoPosition(departureLat, departureLong), new ImageIcon("Transistor/src/main/resources/blueDot.png")); //add Departure
        jXMapViewer.repaint();
    }
}