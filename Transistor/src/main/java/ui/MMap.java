package ui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

public class MMap extends JPanel{

    private final double LAT_CENTER = 50.8471966;
    private final double LON_CENTER = 5.7015544;
    private JXMapViewer jXMapViewer;

    public MMap(JXMapViewer jXMapViewer) {
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

        //  Create event mouse move
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
    }

    private void initComponents() {

        GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
                jXMapViewerLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jXMapViewerLayout.createSequentialGroup()
                                .addContainerGap(992, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
                jXMapViewerLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jXMapViewerLayout.createSequentialGroup()
                                .addGap(0, 640, Short.MAX_VALUE))
        );


    }
}