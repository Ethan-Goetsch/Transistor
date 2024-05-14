package ui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.MouseInputListener;
import database.DatabaseManager;
import database.queries.BusStopTimesQuery;
import entities.Coordinate;
import entities.Path;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.*;
import ui.CustomComponents.ArrivingTimePanel;
import ui.CustomComponents.MapViewer;
import ui.CustomComponents.CustomWaypoint;
import java.awt.*;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MMap extends JPanel{
    private final double LAT_CENTER = 50.8471966;
    private final double LON_CENTER = 5.7015544;
    private final int mainWidth;
    private final int mainHeight;
    private final JXMapViewer jXMapViewer;

    private final ArrivingTimePanel infopanel;
//6218ap 6228bp test values

    public MMap(JXMapViewer jXMapViewer,ArrivingTimePanel infopanel, int mainWidth, int mainHeight) {
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        changeSize(mainWidth, mainHeight);
        this.jXMapViewer = jXMapViewer;
        this.infopanel = infopanel;
        initLayout();
        initMap();
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
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
                                .addComponent(jXMapViewer, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jXMapViewer, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
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
    public void updateResults(Coordinate departure, Coordinate arrival, Path Path, double distance){

        double departureLong = departure.getLongitude();
        double departureLat = departure.getLatitude();
        double arrivalLong = arrival.getLongitude();
        double arrivalLat = arrival.getLatitude();

        updateMap(Path, new GeoPosition(departureLat,departureLong), new GeoPosition(arrivalLat,arrivalLong));
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

    private void updateMap(Path Path, GeoPosition departure, GeoPosition arrival) {
        ((MapViewer)jXMapViewer).setPath(Path);
        //TODO here add the different icons that are needed
        ((MapViewer) jXMapViewer).removeWaypoints();
        infopanel.clearBusStopInfo();
        ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(departure, new ImageIcon("Transistor/src/main/resources/locationIcon.png"), -1, infopanel));
        ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(arrival, new ImageIcon("Transistor/src/main/resources/blueDot.png"), -1, infopanel));
        ArrayList<entities.Point> sp = new ArrayList<>();
        sp.add(new entities.Point(new Coordinate(51.932576, 4.401493),2521959)); //test
        sp.add(new entities.Point(new Coordinate(51.93752, 4.384413),2522368)); //test
        for (entities.Point p : sp) {
            infopanel.addBusStopInfo(p.getID(),getArrivingTimesOfBus(p.getID()));
            ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint( new GeoPosition(p.getCoordinate().getLatitude(),p.getCoordinate().getLongitude()), new ImageIcon("Transistor/src/main/resources/blueDot.png"), p.getID(), infopanel));

        }
    }

    private ArrayList<LocalTime> getArrivingTimesOfBus(int stopID){
        DatabaseManager db = DatabaseManager.getInstance();
        ResultSet res =  db.executeQuery(new BusStopTimesQuery(stopID).getStatement());
        ArrayList<LocalTime> arrivals = new ArrayList<>();
        try{
            while ( res.next() ) {
                String arrival = res.getString(1);
                arrivals.add(LocalTime.parse(arrival));

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        Collections.sort(arrivals);
        LocalTime now = LocalTime.now();

        // Remove all times that have passed the current time
        arrivals.removeIf(time -> time.isBefore(now));
        return arrivals;
    }
}