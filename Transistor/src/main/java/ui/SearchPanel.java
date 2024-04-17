package ui;

import entities.RouteRequest;
import entities.RouteType;
import entities.TransportType;
import ui.CustomComponents.CalculationButton;
import ui.CustomComponents.CircularIconButton;
import ui.CustomComponents.CircularIconButtonFactory;
import ui.CustomComponents.InputTextField;
import utils.IAction;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SearchPanel extends JPanel {
    private final IAction<RouteRequest> onCalculateClicked;
    private JTextField departureField;
    private JTextField arrivalField;
    private JLabel distanceLabel;
    private JLabel timeLabel;

    private RouteType routetypeSetting;
    private TransportType selectedTransport;

    public SearchPanel(int mainWidth, int mainHeight, IAction<RouteRequest> onCalculateCLicked) {
        this.setPreferredSize(new Dimension(mainWidth / 3, mainHeight));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding
        initComponents();
        this.onCalculateClicked = onCalculateCLicked;
        routetypeSetting = RouteType.ACTUAL;
        selectedTransport = TransportType.FOOT;
    }

    public void initComponents() {

        departureField = new InputTextField("Choose starting post code...");
        arrivalField = new InputTextField("Choose destination post code...");

        // Change to enums for later
        ButtonGroup transportButonGroup = new ButtonGroup();
        CircularIconButtonFactory buttonFactory = new CircularIconButtonFactory();
        CircularIconButton transportationType1 = buttonFactory.createIconButton("Transistor/src/main/resources/images/walk.png");
        addactionListener(transportationType1, TransportType.FOOT);
        CircularIconButton transportationType2 = buttonFactory.createIconButton("Transistor/src/main/resources/images/bike.png");
        addactionListener(transportationType2, TransportType.BIKE);
        CircularIconButton transportationType3 = buttonFactory.createIconButton("Transistor/src/main/resources/images/bus.png");
        addactionListener(transportationType3, TransportType.FOOT);//TODO change when added bus
        CircularIconButton transportationType4 = buttonFactory.createIconButton("Transistor/src/main/resources/images/car.png");
        addactionListener(transportationType4, TransportType.CAR);
        transportButonGroup.add(transportationType1);
        transportButonGroup.add(transportationType2);
        transportButonGroup.add(transportationType3);
        transportButonGroup.add(transportationType4);


        JButton calculateButton = new CalculationButton("Calculate");
        calculateButton.addActionListener(e -> onCalculateClicked.execute(new RouteRequest(departureField.getText(),
                arrivalField.getText(), selectedTransport, (RouteType) routetypeSetting)));

        distanceLabel = new JLabel();
        timeLabel = new JLabel();
        JRadioButton switchButon = buttonFactory.createIconButton("Transistor/src/main/resources/images/switch.png");

        switchButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!((InputTextField)arrivalField).getDefaultText().equals(arrivalField.getText())
                        && !((InputTextField)departureField).getDefaultText().equals(departureField.getText())){
                    String save = arrivalField.getText();
                    arrivalField.setText(departureField.getText());
                    departureField.setText(save);
                }
            }
        });

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
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED , 35, 35)
                                        .addComponent(transportationType2)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, 35)
                                        .addComponent(transportationType3)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, 35)
                                        .addComponent(transportationType4)
                                        .addContainerGap())
                                        .addComponent(distanceLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(timeLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(125, 125, 125)
                                                .addComponent(switchButon))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(departureField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(switchButon)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(arrivalField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(transportationType1)
                                        .addComponent(transportationType2)
                                        .addComponent(transportationType3)
                                        .addComponent(transportationType4)

                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(distanceLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(timeLabel, GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

    }

    private void addactionListener(JRadioButton button, TransportType type){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTransport = type;

            }
        });
    }
    public void updateResults(String distance, String time)
    {
        distanceLabel.setText(distance);
        timeLabel.setText(time);
    }

    public void setRouteType(RouteType routeType){
        this.routetypeSetting = routeType;
    }
}