package ui.CustomComponents;

import entities.Path;
import org.jxmapviewer.JXMapViewer;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class MapViewer extends JXMapViewer
{
    private Path path;

    public MapViewer()
    {
        path = new Path(new ArrayList<>());
    }

    public void setPath(Path path)
    {
        this.path = path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (path.coordinates().size() <= 1) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D p = new Path2D.Double();

        for (var i = 0; i < path.coordinates().size(); i++)
        {
            var coordinate = path.coordinates().get(i);
            if (i == 0)
            {
                p.moveTo(coordinate.getLatitude(), coordinate.getLatitude());
                // TODO: Draw start coordinate
                continue;
            }
            else if (i == path.coordinates().size() - 1)
            {
                // TODO: Draw end coordinate
            }

            p.lineTo(coordinate.getLatitude(), coordinate.getLongitude());
        }

        g2.setColor(new Color(12, 18, 222));
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(p);
        g2.dispose();
    }
}