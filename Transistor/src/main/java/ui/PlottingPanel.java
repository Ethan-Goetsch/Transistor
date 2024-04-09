package ui;

import com.graphhopper.ResponsePath;
import com.graphhopper.util.shapes.GHPoint3D;
import entities.Coordinate;

import javax.swing.*;
import java.awt.*;

public class PlottingPanel extends JPanel
{

    // Change to UI Controller to set the plotting pannels
    double departureLong = 0;
    double departureLat = 0;

    double arrivalLong = 0;
    double arrivalLat = 0;
    LatLonPixelConverter latLonPixelConverter;

    private ResponsePath bestPath; 

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;

    public PlottingPanel(int width, int height)
    {
        this.bestPath = null;
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        latLonPixelConverter = new LatLonPixelConverter();
        setVisible(true);
        setOpaque(false);
    }

    public void updateResults(Coordinate departure, Coordinate arrival, ResponsePath bestPath)
    {
        this.departureLong = departure.getLongitude();
        this.departureLat = departure.getLatitude();
        this.arrivalLong = arrival.getLongitude();
        this.arrivalLat = arrival.getLatitude();
        this.bestPath = bestPath;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g1)
    {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D)g1;

        g.translate(130, 78);

        int x1 = latLonPixelConverter.convertLongitudeToPixel(departureLong, PANEL_WIDTH);
        int y1 = latLonPixelConverter.convertLatitudeToPixel(departureLat, PANEL_HEIGHT);

        ImageIcon Icon = new ImageIcon("Transistor/src/main/resources/LocationIcon.png");

        // Resize images if needed
        Image scaledDepartureImage = Icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        Icon = new ImageIcon(scaledDepartureImage);

        int x2 = latLonPixelConverter.convertLongitudeToPixel(arrivalLong, PANEL_WIDTH);
        int y2 = latLonPixelConverter.convertLatitudeToPixel(arrivalLat, PANEL_HEIGHT);
        g.drawImage(Icon.getImage(), x2 - 20, y2 - 35, null); // Draw arrival image

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLACK);
        
        g.fillOval(x1 - 5, y1 - 5, 10, 10); // Circle size of 10

        g.setColor(Color.RED);

        if (bestPath == null)
        {
            g.drawLine(x1, y1, x2, y2);
        }
        else
        {
            var bestPoints = bestPath.getPoints();

            GHPoint3D lastPoint = bestPoints.get(0);
            for (int i = 1; i < bestPoints.size(); i++)
            {
                GHPoint3D currentPoint3d = bestPoints.get(i);
                int lx1 = latLonPixelConverter.convertLongitudeToPixel(lastPoint.getLon(), PANEL_WIDTH);
                int ly1 = latLonPixelConverter.convertLatitudeToPixel(lastPoint.getLat(), PANEL_HEIGHT);

                int lx2 = latLonPixelConverter.convertLongitudeToPixel(currentPoint3d.getLon(), PANEL_WIDTH);
                int ly2 = latLonPixelConverter.convertLatitudeToPixel(currentPoint3d.getLat(), PANEL_HEIGHT);

                g.drawLine(lx1, ly1, lx2, ly2);

                lastPoint = currentPoint3d;

            }
        }

    }

}
