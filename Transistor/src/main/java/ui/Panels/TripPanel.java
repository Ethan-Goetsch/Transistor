package ui.Panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TripPanel extends JPanel {
    private OutputRoutingSearchPanel outputRoutingSearchPanel;
    private int mainWidth;
    private int mainHeight;
    private InputRoutingSearchPanel inputRoutingSearchPanel;
    public TripPanel(JPanel searchPanel, int mainWidth, int mainHeight){
        this.inputRoutingSearchPanel = (InputRoutingSearchPanel) searchPanel;
        outputRoutingSearchPanel = new OutputRoutingSearchPanel(mainWidth, mainHeight);
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;

        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding
        changeSize(mainWidth, mainHeight);
        setLayout();
    }

    private void setLayout() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(inputRoutingSearchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(outputRoutingSearchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(inputRoutingSearchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(outputRoutingSearchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup())
        );
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth / 3,  mainHeight));
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.inputRoutingSearchPanel.changeSize(mainWidth, mainHeight);
        this.outputRoutingSearchPanel.changeSize(mainWidth, mainHeight);
    }

    public OutputRoutingSearchPanel getTripInformationPanel(){
        return outputRoutingSearchPanel;
    }
}
