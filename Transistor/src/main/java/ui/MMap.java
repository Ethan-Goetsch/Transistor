package ui;

import entities.Coordinate;
import entities.Path;
import entities.PathPoint;
import entities.Trip;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import ui.CustomComponents.ArrivingTimePanel;
import ui.CustomComponents.CustomWaypoint;
import ui.CustomComponents.MapViewer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MMap extends JPanel
{
    private final double LAT_CENTER = 50.8471966;
    private final double LON_CENTER = 5.7015544;
    private final int mainWidth;
    private final int mainHeight;
    private final JXMapViewer jXMapViewer;

    private final ArrivingTimePanel infopanel;
//6218ap 6228bp test values

    public MMap(JXMapViewer jXMapViewer, ArrivingTimePanel infopanel, int mainWidth, int mainHeight)
    {
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        changeSize(mainWidth, mainHeight);
        this.jXMapViewer = jXMapViewer;
        this.infopanel = infopanel;
        initLayout();
        initMap();
    }

    public void changeSize(int mainWidth, int mainHeight)
    {
        this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
    }

    private void initMap()
    {
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

    private void initLayout()
    {

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

    public void updateResults(Coordinate departure, Coordinate arrival, List<Trip> trips, double distance)
    {
        var paths = trips.stream().map(Trip::path).toList();
        var departureLong = departure.getLongitude();
        var departureLat = departure.getLatitude();
        var arrivalLong = arrival.getLongitude();
        var arrivalLat = arrival.getLatitude();

        updateMap(paths, new GeoPosition(departureLat, departureLong), new GeoPosition(arrivalLat, arrivalLong));
        setView(new GeoPosition(departureLat, departureLong), new GeoPosition(arrivalLat, arrivalLong));
        jXMapViewer.repaint();
    }

    private void setView(GeoPosition departure, GeoPosition arrival)
    {
        GeoPosition centerPosition = new GeoPosition((departure.getLatitude() + arrival.getLatitude()) / 2, (departure.getLongitude() + arrival.getLongitude()) / 2);
        jXMapViewer.setAddressLocation(centerPosition);
        Set<GeoPosition> positions = new HashSet<>();
        positions.add(departure);
        positions.add(arrival);
        jXMapViewer.calculateZoomFrom(positions);
    }

    // TODO: CHANGE THIS TO NOT DEPEND ON THE DATABASE ENTIRELY
    private void updateMap(List<Path> paths, GeoPosition departure, GeoPosition arrival)
    {
        ((MapViewer) jXMapViewer).setPaths(paths);
        //TODO here add the different icons that are needed
        ((MapViewer) jXMapViewer).removeWaypoints();
        infopanel.clearBusStopInfo();
        ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(departure, new ImageIcon("Transistor/src/main/resources/locationIcon.png"), -1, infopanel));
        ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(arrival, new ImageIcon("Transistor/src/main/resources/blueDot.png"), -1, infopanel));
        ArrayList<PathPoint> sp = new ArrayList<>();

//        sp.add(new PathPoint(new Coordinate(51.932576, 4.401493),2521959)); //test
//        sp.add(new PathPoint(new Coordinate(51.93752, 4.384413),2522368)); //test

        for (Path path : paths)
        {
            for (var point : path.points())
            {
                var icon = new ImageIcon("Transistor/src/main/resources/blueDot.png");
                var waypoint = new CustomWaypoint(new GeoPosition(point.coordinate().getLatitude(), point.coordinate().getLongitude()), icon, -1, infopanel);
                ((MapViewer) jXMapViewer).addWaypoint(waypoint);
            }
        }

//        for (PathPoint p : sp)
//        {
//            infopanel.addBusStopInfo(p.getID(),getArrivingTimesOfBus(p.getID()));
//            ((MapViewer) jXMapViewer).addWaypoint(new CustomWaypoint(new GeoPosition(p.coordinate().getLatitude(), p.coordinate().getLongitude()), new ImageIcon("Transistor/src/main/resources/blueDot.png"), -1, infopanel));
//        }

        // TODO: CHANGE THIS TO NOT RELY ON THE DATABASE ENTIRELY
//    private ArrayList<LocalTime> getArrivingTimesOfBus(int stopID)
//    {
//        DatabaseManager db = DatabaseManager.getInstance();
//        ResultSet res =  db.executeQuery(new BusStopTimesQuery(stopID).getStatement());
//        ArrayList<LocalTime> arrivals = new ArrayList<>();
//        try{
//            while ( res.next() ) {
//                String arrival = res.getString(1);
//                arrivals.add(LocalTime.parse(arrival));
//
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        Collections.sort(arrivals);
//        LocalTime now = LocalTime.now();
//
//        // Remove all times that have passed the current time
//        arrivals.removeIf(time -> time.isBefore(now));
//        return arrivals;
//    }
    }
}