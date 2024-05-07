package ui.CustomComponents;

import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomWaypoint{
    private final JButton representation;
    private final GeoPosition geoPosition;
    private ArrivingTimePanel infoPanel;
    private int ID;
    private CustomWaypoint(GeoPosition geoPosition, ImageIcon representation){
        this.geoPosition = geoPosition;
        this.representation = new JButton();
        setRepresentation(representation);
        ID = -1;// non bus waypoint representstion
    }
    public CustomWaypoint(GeoPosition geoPosition, ImageIcon representation, int ID, ArrivingTimePanel infoPanel){
        this(geoPosition,representation);
        this.ID = ID;
        this.infoPanel = infoPanel;
    }

    private void setRepresentation(ImageIcon representation) {
        this.representation.setIcon(representation);
        this.representation.setSize(new Dimension(30, 30));
        this.representation.setBackground(new Color(255,255,255,0));
        this.representation.setBorderPainted(false);
        this.representation.setFocusPainted(false);
        this.representation.setContentAreaFilled(false);

        this.representation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    //TODO add bus times displayed when clicked
                if(ID != -1){
                    infoPanel.display(ID);
                }
                else{
                    infoPanel.clearInfo();
                }

            }
        });
    }

    public JButton getRepresentation(){
        return representation;
    }

    public GeoPosition getGeoPosition(){
        return this.geoPosition;
    }

    public void setLocationRepresentation(int x, int y){
        representation.setLocation(x,y);
    }

}
