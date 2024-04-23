package ui.CustomComponents;

import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomWaypoint{
    private final JButton representation;
    private final GeoPosition geoPosition;
    public CustomWaypoint(GeoPosition geoPosition, ImageIcon representation){
        this.geoPosition = geoPosition;
        this.representation = new JButton();
        this.representation.setIcon(representation);
        this.representation.setSize(new Dimension(30, 30));
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
