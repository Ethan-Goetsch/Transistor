package ui;
import utils.Coordinates;

import javax.swing.*;
import java.awt.*;

public class PlottingPanel extends JPanel {

    // Change to UI Controller to set the plotting pannels
    double departureLong = 0;
    double departureLat = 0;

    double arrivalLong = 0;
    double arrivalLat = 0;
    LatLonPixelConverter latLonPixelConverter;
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;


    public PlottingPanel(int width, int height) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        latLonPixelConverter = new LatLonPixelConverter();
        setVisible(true);
        setOpaque(false);
    }

    public void updateResults(Coordinates departure, Coordinates arrival) {
        this.departureLong = departure.getLongitude();
        this.departureLat = departure.getLatitude();
        this.arrivalLong = arrival.getLongitude();
        this.arrivalLat = arrival.getLatitude();
        this.repaint();
        System.out.println(departureLong);
        System.out.println(departureLat);

        System.out.println(arrivalLong);

        System.out.println(arrivalLat);

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.translate(130,78);
        System.out.println(departureLong);
        int x1 = latLonPixelConverter.convertLongitudeToPixel(departureLong, PANEL_WIDTH);
        int y1 = latLonPixelConverter.convertLatitudeToPixel(departureLat, PANEL_HEIGHT);

        System.out.println("x1: " + x1 + " y1: " + y1);
        ImageIcon Icon = new ImageIcon("Transistor/src/main/resources/LocationIcon.png");


        // Resize images if needed
        Image scaledDepartureImage = Icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        Icon = new ImageIcon(scaledDepartureImage);
//        g.drawImage(Icon.getImage(), x1 - 20, y1 - 35, null); // Draw departure image


        int x2 = latLonPixelConverter.convertLongitudeToPixel(arrivalLong, PANEL_WIDTH);
        int y2 = latLonPixelConverter.convertLatitudeToPixel(arrivalLat, PANEL_HEIGHT);
        g.drawImage(Icon.getImage(), x2 - 20, y2 - 35, null); // Draw arrival image

        g.setColor(Color.RED);
        g.fillOval(x1 - 5, y1 - 5, 10, 10); // Circle size of 10
//        g.fillOval(x2 - 5, y2 - 5, 10, 10); // Circle size of 10

        g.drawLine(x1,y1,x2,y2);

//        g.fillOval(100, 100, 10, 10);

    }

}
