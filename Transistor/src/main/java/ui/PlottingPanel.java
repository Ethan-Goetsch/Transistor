package ui;
import javax.swing.*;
import java.awt.*;

public class PlottingPanel extends JPanel {

    // Change to UI Controller to set the plotting pannels
    double departureLong = 5.69962724999999;
    double departureLat = 50.8504953534482;

    double arrivalLong = 5.71148493333333;
    double arrivalLat = 50.85796175 ;
    LatLonPixelConverter latLonPixelConverter;
    private int PANEL_WIDTH;
    private int PANEL_HEIGHT;


    public PlottingPanel(int width, int height) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        latLonPixelConverter = new LatLonPixelConverter();
        setVisible(true);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setOpaque(false);
        System.out.println(this.isVisible());
        System.out.println("Testing plotting Wyck 6221AC to 6224XM");



    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.translate(130,78);

        int x1 = latLonPixelConverter.convertLongitudeToPixel(departureLong, PANEL_WIDTH);
        int y1 = latLonPixelConverter.convertLatitudeToPixel(departureLat, PANEL_HEIGHT);

        ImageIcon Icon = new ImageIcon("Transistor/src/main/resources/LocationIcon.png");


        // Resize images if needed
        Image scaledDepartureImage = Icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        Icon = new ImageIcon(scaledDepartureImage);
//        g.drawImage(Icon.getImage(), x1 - 20, y1 - 35, null); // Draw departure image


        int x2 = latLonPixelConverter.convertLongitudeToPixel(arrivalLong, PANEL_WIDTH);
        int y2 = latLonPixelConverter.convertLatitudeToPixel(arrivalLat, PANEL_HEIGHT);
        g.drawImage(Icon.getImage(), x2 - 20, y2 - 35, null); // Draw arrival image


        System.out.println("X: " + x1 + ", Y: " + y1); // Print to the console

        g.setColor(Color.RED);
        g.fillOval(x1 - 5, y1 - 5, 10, 10); // Circle size of 10
//        g.fillOval(x2 - 5, y2 - 5, 10, 10); // Circle size of 10

        g.drawLine(x1,y1,x2,y2);

//        g.fillOval(100, 100, 10, 10);

    }

}
