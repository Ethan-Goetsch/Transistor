package ui.CustomComponents;

import com.graphhopper.ResponsePath;
import entities.Path;
import entities.RouteType;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class MapViewer extends JXMapViewer {
    private Path path;
    public MapViewer(){
        routeType = RouteType.ACTUAL;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (path.points().size() <= 1) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D p = new Path2D.Double();

        for (var i = 0; i < path.points().size(); i++)
        {
            var point = path.points().get(i);
            if (i == 0)
            {
                p.moveTo(point.getX(), point.getY());
                // TODO: Draw start point
                continue;
            }
            else if (i == path.points().size() - 1)
            {
                // TODO: Draw end point
            }

            p.lineTo(point.getX(), point.getY());
        }

        g2.setColor(new Color(12, 18, 222));
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(p);
        g2.dispose();
    }
    public void setPath(Path path){
        this.path = path;
    }
    public void changeRouteType(RouteType routeType){
        this.routeType = routeType;
    }
}