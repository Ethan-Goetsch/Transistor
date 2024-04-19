package ui.CustomComponents;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.shapes.GHPoint3D;
import entities.RouteType;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.function.Consumer;
public class MapViewer extends JXMapViewer {
    private ResponsePath bestPath;
    private boolean firstPoint;

    private RouteType routeType;
    public MapViewer(){
        routeType = RouteType.ACTUAL;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bestPath != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Path2D p = new Path2D.Double();
            firstPoint = true;
            if(routeType == RouteType.ACTUAL){
                bestPath.getPoints().forEach(new Consumer<GHPoint3D>() {
                    @Override
                    public void accept(GHPoint3D ghPoint3D) {
                        Point2D point = convertGeoPositionToPoint(new GeoPosition(ghPoint3D.getLat(),ghPoint3D.getLon() ));
                        if(firstPoint){
                            firstPoint = false;
                            p.moveTo(point.getX(), point.getY());
                        }else{
                            p.lineTo(point.getX(), point.getY());
                        }
                    }
                });
            }
            else{
                bestPath.getWaypoints().forEach(new Consumer<GHPoint3D>() {
                    @Override
                    public void accept(GHPoint3D ghPoint3D) {
                        Point2D point = convertGeoPositionToPoint(new GeoPosition(ghPoint3D.getLat(),ghPoint3D.getLon() ));
                        if(firstPoint){
                            firstPoint = false;
                            p.moveTo(point.getX(), point.getY());
                        }else{
                            p.lineTo(point.getX(), point.getY());
                        }
                    }
                });
            }
            g2.setColor(new Color(12, 18, 222));
            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(p);
            g2.dispose();
        }
    }
    public void bestPath(ResponsePath bestPath){
        this.bestPath = bestPath;
    }
    public void changeRouteType(RouteType routeType){
        this.routeType = routeType;
    }
}
