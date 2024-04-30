package ui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.MouseInputListener;

import entities.Coordinate;
import entities.Path;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.*;
import ui.CustomComponents.MapViewer;
import ui.CustomComponents.CustomWaypoint;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MMap extends JPanel{
    private final double LAT_CENTER = 50.8471966;
    private final double LON_CENTER = 5.7015544;
    private final int mainWidth;
    private final int mainHeight;
    private final JXMapViewer jXMapViewer;
//6218ap 6228bp test values

    public MMap(JXMapViewer jXMapViewer, int mainWidth, int mainHeight) {
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
        this.jXMapViewer = jXMapViewer;
        initLayout();
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


    private void initLayout() {

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

        GroupLayout jXMapViewerLayout = new GroupLayout(jXMapViewer);
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
    public void updateResults(Coordinate departure, Coordinate arrival, Path path, double distance){

        double departureLong = departure.getLongitude();
        double departureLat = departure.getLatitude();
        double arrivalLong = arrival.getLongitude();
        double arrivalLat = arrival.getLatitude();

        updateMap(path, new GeoPosition(departureLat,departureLong), new GeoPosition(arrivalLat,arrivalLong));
        setView(new GeoPosition(departureLat,departureLong), new GeoPosition(arrivalLat,arrivalLong));
        jXMapViewer.repaint();
    }

    private void setView(GeoPosition departure, GeoPosition arrival) {
        GeoPosition centerPosition = new GeoPosition((departure.getLatitude() + arrival.getLatitude()) / 2, (departure.getLongitude() + arrival.getLongitude()) / 2);
        jXMapViewer.setAddressLocation(centerPosition);
        Set<GeoPosition> positions = new HashSet<>();
        positions.add(departure);
        positions.add(arrival);
        jXMapViewer.calculateZoomFrom(positions);
    }

    private void updateMap(Path path, GeoPosition departure, GeoPosition arrival) {
        ((MapViewer)jXMapViewer).setPath(path);
        //TODO here add the different icons that are needed
        ((MapViewer) jXMapViewer).removeWaypoints();
        ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(departure, new ImageIcon("Transistor/src/main/resources/locationIcon.png")));
        ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(arrival, new ImageIcon("Transistor/src/main/resources/blueDot.png")));


    }
}