package ui.CustomComponents;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;

public class Waypoint extends DefaultWaypoint {
    private JButton representation;
    public Waypoint(GeoPosition position, ImageIcon representation){
        super(position);
        this.representation = new JButton();
        this.representation.setIcon(representation);
    }
    public JButton getRepresentation(){
        return representation;
    }

}
