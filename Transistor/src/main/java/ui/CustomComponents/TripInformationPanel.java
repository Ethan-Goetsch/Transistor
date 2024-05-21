package ui.CustomComponents;

import entities.Route;
import entities.TransportType;
import entities.Trip;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TripInformationPanel extends JPanel implements Resizible {
    private JPanel contents;
    private List<Resizible> components;

    public TripInformationPanel(int mainWidth, int mainHeight) {
        components = new ArrayList<>();
        changeSize(mainWidth, mainHeight);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        contents = createContentsPanel();
        JScrollPane scrollPane = createScrollPane(contents);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createContentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JScrollPane createScrollPane(JPanel contents) {
        JScrollPane scrollPane = new JScrollPane(contents);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension(mainWidth / 3, mainHeight / 3));
        components.forEach(c -> c.changeSize((int) this.getPreferredSize().getWidth(), (int) this.getPreferredSize().getHeight()));
    }

    public void clearInfo() {
        contents.removeAll();
        components.clear();
        this.revalidate();
        this.repaint();
    }

    public void updateResults(Route route) {
        clearInfo();
        addInfoPanel("Departure at: " + route.arrivalDescription(), true);

        for (Trip trip : route.trips()) {
            if (trip.type() == TransportType.BUS) {
                addTripInfoPanels(trip);
            }
        }

        addInfoPanel("At your destination at: " + route.arrivalDescription(), true);
        contents.revalidate();
        this.revalidate();
        this.repaint();
    }

    private void addInfoPanel(String text, boolean blueDot) {
        WalkingInfoPanel infoPanel = createInfoPanel(text, blueDot);
        contents.add(infoPanel);
        components.add(infoPanel);
        addSeparator();
    }

    private WalkingInfoPanel createInfoPanel(String text, boolean blueDot) {
        WalkingInfoPanel panel = new WalkingInfoPanel((int) this.getPreferredSize().getWidth(), (int) this.getPreferredSize().getHeight());
        panel.setBackground(Color.white);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        if(blueDot){
            CircularIconButton b = new CircularIconButton(new ImageIcon("Transistor/src/main/resources/infoPanelBlueDot.png"));
            panel.add(b);
        }
        panel.add(new JLabel(text));
        return panel;
    }

    private void addTripInfoPanels(Trip trip) {
        addInfoPanel("Take bus from " + trip.nodes().getFirst().name(), true);
        addInfoPanel("Next bus arriving at " + trip.nodes().getFirst().arrivalTime(), false);
        BusStopInfoPanel busStopInfo = new BusStopInfoPanel((int) this.getPreferredSize().getWidth(), (int) this.getPreferredSize().getHeight());
        busStopInfo.display(trip);
        contents.add(busStopInfo);
        components.add(busStopInfo);
        addSeparator();
        addInfoPanel("Arrive at " + trip.nodes().getLast().name(), true);
        addInfoPanel("Bus arrives at destination at " + trip.nodes().getLast().arrivalTime(), false);
    }

    private void addSeparator() {
        JPanel separator = new JPanel();
        separator.setMaximumSize(new Dimension(getWidth(), 2));
        separator.setPreferredSize(new Dimension(getWidth(), 2));
        separator.setBackground(new Color(35, 98, 223, 128));
        contents.add(separator);
    }
}
