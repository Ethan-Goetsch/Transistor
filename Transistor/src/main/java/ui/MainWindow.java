package ui;

import entities.Request;
import org.jxmapviewer.JXMapViewer;
import ui.Panels.AccessibilityPanel;
import ui.Menus.AplicationMenuBar;
import ui.CustomComponents.MapViewer;
import ui.Panels.InputRoutingSearchPanel;
import ui.Panels.OutputAccessibilityPanel;
import ui.Panels.TripPanel;
import utils.IObservable;
import utils.Subject;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private int mainWidth = 990;
    private int mainHeight = 770;
    private InputRoutingSearchPanel inputRoutingSearchPanel;
    private JXMapViewer jXMapViewer;
    private MMap map;
    private TripPanel tripPanel;
    private AccessibilityPanel accessibilityPanel;
    private InformationPanel informationPanel;
    private final Subject<Request> onRequested;

    public MainWindow() {
        this.onRequested = new Subject<>();
        this.setFocusable(true);
        this.setBackground(Color.white);
        this.setVisible(true);
        configureWindow();
        initializeElements();
        setLayout();

    }

    public IObservable<Request> getRequest() {
        return onRequested;
    }

    public void configureWindow() {
        this.setSize(mainWidth+14, mainHeight+60);
        this.setTitle("Maastricht maps");
        this.setIconImage((new ImageIcon("Transistor/src/main/resources/images/applicationIcon.png")).getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeElements() {

        jXMapViewer = new MapViewer(mainWidth, mainHeight);
        map = new MMap(jXMapViewer,mainWidth, mainHeight);
        inputRoutingSearchPanel = new InputRoutingSearchPanel(mainWidth, mainHeight, onRequested::execute); // this is the search panel from before
        tripPanel = new TripPanel(inputRoutingSearchPanel, mainWidth, mainHeight);
        accessibilityPanel = new AccessibilityPanel( mainWidth, mainHeight, onRequested::execute);
        informationPanel = new InformationPanel(tripPanel, accessibilityPanel, mainWidth, mainHeight);
        getContentPane().add(map);
        getContentPane().add(inputRoutingSearchPanel);
        this.setJMenuBar(new AplicationMenuBar(this));

    }

    private void setLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(informationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(map, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

    public void setMainHeight(int mainHeight) {
        this.mainHeight = mainHeight;
        changeSize();
    }
    public void setMainWidth(int mainWidth) {
        this.mainWidth = mainWidth;
        changeSize();
    }
    public JXMapViewer getjXMapViewer() { return jXMapViewer;}

    public MMap getMap() {
        return map;
    }
    public InputRoutingSearchPanel getInputRoutingSearchPanel() {return this.inputRoutingSearchPanel;}

    public OutputAccessibilityPanel getOutputAccessibilityPanel(){
        return this.accessibilityPanel.getOutputPanel();
    }

    public TripPanel getTripPanel(){
        return this.tripPanel;
    }
    public InformationPanel getInformationPanel() {
        return this.informationPanel;
    }
    private void changeSize(){
        this.setSize(mainWidth+14, mainHeight+60);
        this.map.changeSize(mainWidth, mainHeight);
        this.informationPanel.changeSize(mainWidth, mainHeight);;
        ((MapViewer)this.jXMapViewer).changeSize(mainWidth, mainHeight);
    }


}