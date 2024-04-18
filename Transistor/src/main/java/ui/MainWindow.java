package ui;

import entities.RouteRequest;
import org.jxmapviewer.JXMapViewer;
import ui.Menues.AplicationMenuBar;
import utils.IObservable;
import utils.Subject;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private LayoutManager layout = new BorderLayout();
    private int mainWidth = 900;
    private int mainHeight = 700;
    private SearchPanel searchPanel;
    private JXMapViewer jXMapViewer;
    public SearchPanel getSearchPanel() {
        return this.searchPanel;
    }
    private final Subject<RouteRequest> onRouteRequested;

    public MainWindow() {

        this.onRouteRequested = new Subject<>();
        configureWindow();
        initializeElements();
        this.setFocusable(true);
        this.setBackground(Color.white);
        this.setVisible(true);
    }

    public IObservable<RouteRequest> getRouteRequested() {
        return onRouteRequested;
    }

    public void configureWindow() {
        resize();
        this.setTitle("Maastricht maps");
        this.setIconImage((new ImageIcon("Transistor/src/main/resources/images/applicationIcon.png")).getImage());
        this.setLayout(layout);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeElements() {

        jXMapViewer = new JXMapViewer();
        MMap map = new MMap(jXMapViewer,mainWidth, mainHeight);

        searchPanel = new SearchPanel(mainWidth, mainHeight, onRouteRequested::execute); // this is the search panel from before

        this.add(map, BorderLayout.CENTER);

        this.add(searchPanel, BorderLayout.EAST);

        JMenuBar menues = new AplicationMenuBar(this);
        this.setJMenuBar(menues);


        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(map, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(searchPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(map, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                )
        );

        pack();

    }

    public void setMainHeight(int mainHeight) {
        this.mainHeight = mainHeight;
    }

    public void setMainWidth(int mainWidth) {
        this.mainWidth = mainWidth;
    }

    public SearchPanel searchPanel() {
        return searchPanel;
    }
    public JXMapViewer getjXMapViewer() {
        return jXMapViewer;
    }
    public void resize(){
        this.setSize(mainWidth, mainHeight);
    }
}
