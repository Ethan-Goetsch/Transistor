package ui;

import entities.RouteRequest;
import org.jxmapviewer.JXMapViewer;
import ui.CustomComponents.BusStopInfoPanel;
import ui.CustomComponents.TripInformationPanel;
import ui.Menus.AplicationMenuBar;
import ui.CustomComponents.MapViewer;
import utils.IObservable;
import utils.Subject;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private int mainWidth = 990;
    private int mainHeight = 770;
    private SearchPanel searchPanel;
    private JXMapViewer jXMapViewer;

    private TripInformationPanel tripInformationPanel;
    private MMap map;
    private final Subject<RouteRequest> onRouteRequested;

    public MainWindow() {
        this.onRouteRequested = new Subject<>();
        this.setFocusable(true);
        this.setBackground(Color.white);
        this.setVisible(true);
        configureWindow();
        initializeElements();
        setLayout();

    }

    public IObservable<RouteRequest> getRouteRequested() {
        return onRouteRequested;
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
        searchPanel = new SearchPanel(mainWidth, mainHeight, onRouteRequested::execute); // this is the search panel from before
        tripInformationPanel = new TripInformationPanel(mainWidth, mainHeight);
        getContentPane().add(map);
        getContentPane().add(searchPanel);
        this.setJMenuBar(new AplicationMenuBar(this));

    }

    private void setLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tripInformationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(map, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tripInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    public SearchPanel searchPanel() {
        return searchPanel;
    }
    public JXMapViewer getjXMapViewer() { return jXMapViewer;}
    public MMap getMap() {
        return map;
    }
    public SearchPanel getSearchPanel() {return this.searchPanel;}

    public TripInformationPanel getTripInformationPanel(){
        return this.tripInformationPanel;
    }
    private void changeSize(){
        this.setSize(mainWidth+14, mainHeight+60);
        this.map.changeSize(mainWidth, mainHeight);
        this.searchPanel.changeSize(mainWidth, mainHeight);
        this.tripInformationPanel.changeSize(mainWidth, mainHeight);
        ((MapViewer)this.jXMapViewer).changeSize(mainWidth, mainHeight);
    }

}