package ui.CustomComponents;

import entities.Trip;
import entities.transit.TransitNode;

import javax.swing.*;
import java.awt.*;

public class BusStopInfoPanel extends JPanel implements Resizible {
    private JPanel contents;

    public BusStopInfoPanel(int mainWidth, int mainHeight) {
        changeSize(mainWidth, mainHeight);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        contents.setAlignmentX(Component.LEFT_ALIGNMENT);
        contents.setBackground(Color.white);
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(contents);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension(mainWidth / 3, mainHeight / 3));
    }

    public void clearInfo() {
        contents.removeAll();
        contents.revalidate();
        this.revalidate();
        repaint();
    }

    public void display(Trip trip) {
        clearInfo();
        boolean first = true;
        if (trip != null) {
            for (TransitNode stop : trip.nodes()) {
                JPanel timePanel = new JPanel();
                timePanel.setBackground(Color.white);
                timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                if (first) {
                    first = false;
                    continue;
                } else {
                    String newString = stop.name() .replaceAll("\\b" + "Maastricht, " + "\\b", "");
                    JLabel timeText = new JLabel(stop.arrivalTime() + " at " + newString + "sid: " + stop.id());
                    //JLabel timeText = new JLabel(stop.arrivalTime() + " at " + newString);
                    timePanel.add(timeText);
                }
                contents.add(timePanel);
            }
        } else {
            contents.add(new JLabel("No data available"));
        }
    }
}
