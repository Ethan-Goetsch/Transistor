package ui.Panels;

import entities.RouteRequest;
import entities.RouteType;
import entities.TransitType;
import entities.TransportType;
import ui.CustomComponents.*;
import utils.IAction;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputRoutingSearchPanel extends JPanel
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
    private int mainWidth;
    private int mainHeight;

    public InputRoutingSearchPanel(int mainWidth, int mainHeight, IAction<RouteRequest> onCalculateCLicked) {
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding
        this.onCalculateClicked = onCalculateCLicked;
        routeTypeSetting = RouteType.ACTUAL;
        selectedTransport = TransportType.FOOT;
        changeSize(mainWidth, mainHeight);
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth / 3,  mainHeight /3));
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.removeAll();
        this.initComponents();
    }

    public void initComponents() {

        departureField = new InputTextField("Choose starting post code...");
        arrivalField = new InputTextField("Choose destination post code...");

        // Change to enums for later
        ButtonGroup transportButtonGroup = new ButtonGroup();
        CircularIconButtonFactory buttonFactory = new CircularIconButtonFactory((int)mainWidth/33);
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
                arrivalField.getText(), selectedTransport, routeTypeSetting, TransitType.Transfer)));

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

        JPanel animatedLinePanel = new JPanel();
        animatedLinePanel.setBackground(Color.white);
        animatedLinePanel.setSize(new Dimension((int)(mainWidth/49.5),(int)(mainWidth/3.5)));
        animatedLinePanel.setLayout(new BorderLayout());
        indicator = new AnimatedLine(new Point(transportationType1.getX()+(int)(mainWidth/49.5), transportationType1.getY()), new Point(transportationType1.getX()+(int)(mainWidth/24.75), transportationType1.getY()));
        animatedLinePanel.add(indicator);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGap((int)(mainWidth/40))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(departureField, GroupLayout.PREFERRED_SIZE,(int)(mainWidth/3.5), GroupLayout.PREFERRED_SIZE)
                                        .addComponent(arrivalField, GroupLayout.PREFERRED_SIZE, (int)(mainWidth/3.5), GroupLayout.PREFERRED_SIZE)
                                        .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, (int)(mainWidth/3.5), GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap((int)(mainWidth/100),(int)(mainWidth/100))
                                                .addComponent(transportationType1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED , (int)mainWidth/33, 30)
                                                .addComponent(transportationType2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, mainWidth/33, 30)
                                                .addComponent(transportationType3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, mainWidth/33, 30)
                                                .addComponent(transportationType4)
                                                .addContainerGap())
                                        .addComponent(animatedLinePanel, GroupLayout.PREFERRED_SIZE,(int)(mainWidth/3.5), GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap((int)(mainWidth/7.92), (int)(mainWidth/7.92), (int)(mainWidth/7.92))
                                                .addComponent(switchButton))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(departureField, GroupLayout.PREFERRED_SIZE, mainWidth/33, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(switchButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(arrivalField, GroupLayout.PREFERRED_SIZE, mainWidth/33, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(transportationType1)
                                        .addComponent(transportationType2)
                                        .addComponent(transportationType3)
                                        .addComponent(transportationType4)

                                )
                                .addComponent(animatedLinePanel, GroupLayout.PREFERRED_SIZE,5, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, mainWidth/33, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

    }

    private void addActionListener(JRadioButton button, TransportType type){
        button.addActionListener(e -> {
            selectedTransport = type;
            indicator.moveTo(new Point(button.getX()-15, 0), new Point(button.getX()+20, 0));

        });
    }
    public void updateResults(String distance, String time)
    {
        distanceLabel.setText(distance);
        timeLabel.setText(time);
        routeTypeLabel.setText(routeTypeSetting.toString());//todo never used to display
    }

    public void setRouteType(RouteType routeType){
        this.routeTypeSetting = routeType;
    }

}