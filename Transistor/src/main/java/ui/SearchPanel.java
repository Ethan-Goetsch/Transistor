package ui;

import entities.RouteRequest;
import entities.RouteType;
import entities.TransportType;
import ui.CustomComponents.*;
import utils.IAction;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class SearchPanel extends JPanel
{
    private final IAction<RouteRequest> onCalculateClicked;
    private JTextField departureField;
    private JTextField arrivalField;
    private JLabel distanceLabel;
    private JLabel timeLabel;
    private JLabel routeTypeLabel;
    private RouteType routeTypeSetting;
    private TransportType selectedTransport;
    private AnimatedLine indicator;

    public SearchPanel(int mainWidth, int mainHeight, IAction<RouteRequest> onCalculateCLicked) {
        this.setPreferredSize(new Dimension(mainWidth / 3, mainHeight));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding
        this.onCalculateClicked = onCalculateCLicked;
        routeTypeSetting = RouteType.ACTUAL;
        selectedTransport = TransportType.FOOT;

        initComponents();
    }

    public void initComponents() {

        departureField = new InputTextField("Choose starting post code...");
        arrivalField = new InputTextField("Choose destination post code...");

        // Change to enums for later
        ButtonGroup transportButtonGroup = new ButtonGroup();
        CircularIconButtonFactory buttonFactory = new CircularIconButtonFactory();
        JRadioButton transportationType1 = buttonFactory.createIconButton("Transistor/src/main/resources/images/walk.png");
        addActionListener(transportationType1, TransportType.FOOT);
        JRadioButton transportationType2 = buttonFactory.createIconButton("Transistor/src/main/resources/images/bike.png");
        addActionListener(transportationType2, TransportType.BIKE);
        JRadioButton transportationType3 = buttonFactory.createIconButton("Transistor/src/main/resources/images/bus.png");
        addActionListener(transportationType3, TransportType.FOOT);//TODO change when added bus
        JRadioButton transportationType4 = buttonFactory.createIconButton("Transistor/src/main/resources/images/car.png");
        addActionListener(transportationType4, TransportType.CAR);
        transportButtonGroup.add(transportationType1);
        transportButtonGroup.add(transportationType2);
        transportButtonGroup.add(transportationType3);
        transportButtonGroup.add(transportationType4);


        JButton calculateButton = new CalculationButton("Calculate");
        calculateButton.addActionListener(e -> onCalculateClicked.execute(new RouteRequest(departureField.getText(),
                arrivalField.getText(), selectedTransport, routeTypeSetting)));

        distanceLabel = new JLabel();
        timeLabel = new JLabel();
        routeTypeLabel = new JLabel();
        JRadioButton switchButton = buttonFactory.createIconButton("Transistor/src/main/resources/images/switch.png");

        switchButton.addActionListener(e -> {

            if(!((InputTextField)arrivalField).getDefaultText().equals(arrivalField.getText())
                    && !((InputTextField)departureField).getDefaultText().equals(departureField.getText())){
                String save = arrivalField.getText();
                arrivalField.setText(departureField.getText());
                departureField.setText(save);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.white);
        contentPane.setSize(new Dimension(20,280));
        contentPane.setLayout(new BorderLayout());
        indicator = new AnimatedLine(new Point(transportationType1.getX()+20, transportationType1.getY()), new Point(transportationType1.getX()+40, transportationType1.getY()));
        contentPane.add(indicator);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(departureField, GroupLayout.PREFERRED_SIZE,280, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(arrivalField, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap(10,10)
                                                .addComponent(transportationType1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED , 30, 30)
                                                .addComponent(transportationType2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, 30)
                                                .addComponent(transportationType3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, 30)
                                                .addComponent(transportationType4)
                                                .addContainerGap())
                                        .addComponent(contentPane, GroupLayout.PREFERRED_SIZE,280, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(distanceLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(timeLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(routeTypeLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(125, 125, 125)
                                                .addComponent(switchButton))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(departureField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(switchButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(arrivalField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(transportationType1)
                                        .addComponent(transportationType2)
                                        .addComponent(transportationType3)
                                        .addComponent(transportationType4)

                                )
                                .addComponent(contentPane, GroupLayout.PREFERRED_SIZE,5, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(distanceLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(timeLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(routeTypeLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

    }

    private void addActionListener(JRadioButton button, TransportType type){
        button.addActionListener(e -> {
            selectedTransport = type;
            indicator.moveTo(new Point(button.getX()-2, 0), new Point(button.getX()+20, 0));

        });
    }
    public void updateResults(String distance, String time)
    {
        distanceLabel.setText(distance);
        timeLabel.setText(time);
        routeTypeLabel.setText(routeTypeSetting.toString());
    }

    public void setRouteType(RouteType routeType){
        this.routeTypeSetting = routeType;
    }

}