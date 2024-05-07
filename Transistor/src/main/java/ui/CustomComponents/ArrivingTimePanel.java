package ui.CustomComponents;
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ArrivingTimePanel extends JPanel {
    private HashMap<Integer, ArrayList<LocalTime>> busStopsArrivings;
    private JPanel contents;

    public ArrivingTimePanel(int mainWidth, int mainHeight) {
        busStopsArrivings = new HashMap<>();
        changeSize(mainWidth, mainHeight);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        contents.setAlignmentX(Component.LEFT_ALIGNMENT);
        contents.setBackground(Color.white);
        JScrollPane scrollPane = new JScrollPane(contents);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension(mainWidth / 3, mainHeight / 3));
    }

    public void clearInfo(){
        contents.removeAll();
        contents.revalidate();
        this.revalidate();
        repaint();
    }

    public void display(int ID) {
        clearInfo();
        ArrayList<LocalTime> arrivings = busStopsArrivings.get(ID);

        if (arrivings != null) {
            for (LocalTime time : arrivings) {
                JPanel timePanel = new JPanel();
                timePanel.setBackground(Color.white);
                timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel timeText = new JLabel("Next bus arriving at " + time.toString());
                timePanel.add(timeText);
                contents.add(timePanel);
            }
        } else {
            contents.add(new JLabel("No data available for bus stop with ID: " + ID));
        }

        contents.revalidate();
        this.revalidate();
        repaint();
    }

    public void addBusStopInfo(int ID, ArrayList<LocalTime> arrivingTimes){
        busStopsArrivings.put(ID, arrivingTimes);
    }

    public void clearBusStopInfo(){
        busStopsArrivings.clear();
    }
}
