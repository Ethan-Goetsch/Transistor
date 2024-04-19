package ui.CustomComponents;

import org.jxmapviewer.JXMapViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class WaypointPainter extends org.jxmapviewer.viewer.WaypointPainter<Waypoint> {

    @Override
    protected void doPaint(Graphics2D graphic, JXMapViewer map, int width, int height) {
        for (Waypoint wp:getWaypoints()) {
            Point2D p = map.getTileFactory().geoToPixel(wp.getPosition(),map.getZoom());
            Rectangle rec = map.getViewportBounds();
            int x = (int) (p.getX()-rec.getX());
            int y = (int) (p.getY()-rec.getY());
            JButton representation =  wp.getRepresentation();;
            representation.setCursor(new Cursor(Cursor.HAND_CURSOR));
            representation.setLocation(x-representation.getWidth()/2, y-representation.getHeight());
        }
    }
}
