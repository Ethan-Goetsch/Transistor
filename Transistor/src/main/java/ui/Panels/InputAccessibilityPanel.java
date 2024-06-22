package ui.Panels;

import entities.AccessibilityRequest;
import ui.CustomComponents.CalculationButton;
import ui.CustomComponents.InputTextField;
import utils.IAction;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputAccessibilityPanel extends JPanel {
    private final IAction<AccessibilityRequest> onCalculateClicked;

    private boolean disabledPersonsSetting;
    private JTextField postalCodeField;
    private int mainWidth;
    private int mainHeight;

    public InputAccessibilityPanel(int mainWidth, int mainHeight, IAction<AccessibilityRequest> onCalculateCLicked) {
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding
        this.onCalculateClicked = onCalculateCLicked;
        this.disabledPersonsSetting = false;
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

        postalCodeField = new InputTextField("Choose post code...");

        JButton calculateButton = new CalculationButton("Find Accessibility");
        calculateButton.addActionListener(e -> onCalculateClicked.execute(new AccessibilityRequest(postalCodeField.getText(), disabledPersonsSetting)));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGap((int)(mainWidth/40))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(postalCodeField, GroupLayout.PREFERRED_SIZE,(int)(mainWidth/3.5), GroupLayout.PREFERRED_SIZE)
                                        .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, (int)(mainWidth/3.5), GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(postalCodeField, GroupLayout.PREFERRED_SIZE, mainWidth/33, GroupLayout.PREFERRED_SIZE)
                                .addGap(35)
                                .addComponent(calculateButton, GroupLayout.PREFERRED_SIZE, mainWidth/33, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }

    public void changeDisabledPersonSetting(boolean value){
        disabledPersonsSetting = value;
    }
}