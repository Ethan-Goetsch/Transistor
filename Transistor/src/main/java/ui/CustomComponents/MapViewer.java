package ui.CustomComponents;

import entities.Path;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class MapViewer extends JXMapViewer {
    private List<Path> paths;
    private final ArrayList<CustomWaypoint> waypoints;

    public MapViewer(int mainWidth, int mainHeight) {
        paths = new ArrayList<>();
        waypoints = new ArrayList<>();
        changeSize(mainWidth, mainHeight);
    }

    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension(mainWidth, mainHeight));
    }

    public void setPaths(List<Path> Path) {
        this.paths = Path;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var graphics2D = (Graphics2D) g;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paths.forEach(path -> paintPath(path, graphics2D));
        paintWayPoints(graphics2D);
    }

    private void paintPath(Path path, Graphics2D graphics2D) {
        var path2D = new Path2D.Double();

        for (int i = 0; i < path.points().size(); i++) {
            var point = path.points().get(i).coordinate();
            Point2D uiPoint = convertGeoPositionToPoint(new GeoPosition(point.getLatitude(), point.getLongitude()));

            if (i == 0)
                path2D.moveTo(uiPoint.getX(), uiPoint.getY());
            else
                path2D.lineTo(uiPoint.getX(), uiPoint.getY());
        }

        graphics2D.setColor(path.colour());
        graphics2D.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics2D.draw(path2D);
    }

    private void paintWayPoints(Graphics2D graphics2D) {
        for (CustomWaypoint waypoint : waypoints) {
            Point2D uiPoint = convertGeoPositionToPoint(waypoint.getGeoPosition());
            waypoint.setLocationRepresentation((int) uiPoint.getX() - 15, (int) uiPoint.getY() - 15);
            this.add(waypoint.getRepresentation());
            waypoint.getRepresentation().setLocation((int) uiPoint.getX() - 15, (int) uiPoint.getY() - 15);
        }
    }

    public void addWaypoint(CustomWaypoint waypoint) {
        Point2D uiPoint = convertGeoPositionToPoint(waypoint.getGeoPosition());
        waypoint.setLocationRepresentation((int) uiPoint.getX() - 15, (int) uiPoint.getY() - 15);
        waypoints.add(waypoint);
        this.add(waypoint.getRepresentation());
        repaint();
    }

    public void removeWaypoints() {
        for (CustomWaypoint w : waypoints) {
            this.remove(w.getRepresentation());
        }

        waypoints.clear();
        repaint();
    }

    @Override
    public void setZoom(int zoom) {
        super.setZoom(zoom);
        repaint();
    }

    @Override
    public void setAddressLocation(GeoPosition address) {
        super.setAddressLocation(address);
        repaint();
    }
}
