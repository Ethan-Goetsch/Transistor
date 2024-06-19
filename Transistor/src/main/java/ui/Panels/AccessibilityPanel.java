package ui.Panels;

import entities.AccessibilityRequest;
import utils.IAction;
import javax.swing.*;
import java.awt.*;

public class AccessibilityPanel extends JPanel {
    private InputAccessibilityPanel inputPanel;
    private OutputAccessibilityPanel outputPanel;

    public AccessibilityPanel(int mainWidth, int mainHeight, IAction<AccessibilityRequest> onCalculateCLicked){
        inputPanel = new InputAccessibilityPanel(mainWidth, mainHeight, onCalculateCLicked);
        outputPanel = new OutputAccessibilityPanel(mainWidth, mainHeight);
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
                                        .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(outputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup())
        );
    }

    public void changeSize(int mainWidth, int mainHeight) {
        this.setPreferredSize(new Dimension(mainWidth / 3,  mainHeight));
        inputPanel.changeSize(mainWidth,mainHeight);
        outputPanel.changeSize(mainWidth,mainHeight);
    }

    public OutputAccessibilityPanel getOutputPanel(){
        return outputPanel;
    }
    public InputAccessibilityPanel getInputPanel(){return inputPanel;}
}
