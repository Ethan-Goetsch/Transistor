package ui.CustomComponents;

import entities.Path;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MapViewer extends JXMapViewer {
    private Path path;
    private final ArrayList<CustomWaypoint> waypoints;

    public MapViewer() {
        path = new Path(new ArrayList<>());
        waypoints = new ArrayList<>();
    }

    public void setPath(Path path) {
        this.path = path;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (path.coordinates().size() <= 1) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D p = new Path2D.Double();

        for (int i = 0; i < path.coordinates().size(); i++) {
            var point = path.coordinates().get(i);
            Point2D uiPoint = convertGeoPositionToPoint(new GeoPosition(point.getLatitude(), point.getLongitude()));

            if (i == 0) {
                p.moveTo(uiPoint.getX(), uiPoint.getY());
            } else {
                p.lineTo(uiPoint.getX(), uiPoint.getY());
            }

        }
        paintWayPoints();

        g2.setColor(new Color(12, 18, 222));
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(p);
    }

    private void paintWayPoints() {
        for (CustomWaypoint waypoint:
                waypoints ) {
            Point2D uiPoint = convertGeoPositionToPoint(waypoint.getGeoPosition());
            waypoint.setLocationRepresentation((int) uiPoint.getX() - 15, (int) uiPoint.getY() - 15);
            this.add(waypoint.getRepresentation());
        }
    }

    public void addWaypoint(CustomWaypoint waypoint){
        Point2D uiPoint = convertGeoPositionToPoint(waypoint.getGeoPosition());
        waypoint.setLocationRepresentation((int) uiPoint.getX() - 15, (int) uiPoint.getY() - 15);
        waypoints.add(waypoint);
        this.add(waypoint.getRepresentation());

    }

    public void removeWaypoints(){
        for (CustomWaypoint w:
             waypoints) {
            this.remove(w.getRepresentation());
        }
        waypoints.clear();
    }
}
