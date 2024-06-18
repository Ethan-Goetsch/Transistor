package ui.Panels;

import entities.AmenityCategory;
import ui.CustomComponents.AnimatedBar;
import ui.CustomComponents.Resizible;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OutputAccessibilityPanel extends JPanel {
    private int mainWidth;
    private int mainHeight;
    private List<Resizible> components;
    private JLabel[] indexes;

    public OutputAccessibilityPanel(int mainWidth, int mainHeight) {
        components = new ArrayList<>();
        indexes = new JLabel[7];
        this.mainWidth = mainWidth;
        this.mainHeight = mainHeight;
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(25, 0, 0, 20)); // top, left, bottom, right padding
        changeSize(mainWidth, mainHeight);
        setLayout();
    }

    private void setLayout() {
        // Create the bars
        AnimatedBar bar1 = new AnimatedBar(0, mainWidth, mainHeight); // Example target values
        AnimatedBar bar2 = new AnimatedBar(0, mainWidth, mainHeight);
        AnimatedBar bar3 = new AnimatedBar(0, mainWidth, mainHeight);
        AnimatedBar bar4 = new AnimatedBar(0, mainWidth, mainHeight);
        AnimatedBar bar5 = new AnimatedBar(0, mainWidth, mainHeight);
        AnimatedBar bar6 = new AnimatedBar(0, mainWidth, mainHeight);
        AnimatedBar bar7 = new AnimatedBar(0, mainWidth, mainHeight);
        components.add(bar1);
        components.add(bar2);
        components.add(bar3);
        components.add(bar4);
        components.add(bar5);
        components.add(bar6);
        components.add(bar7);

        // Create the labels for bar names
        JLabel bar1Name = new JLabel(AmenityCategory.HEALTHCARE.getName());
        JLabel bar2Name = new JLabel(AmenityCategory.EDUCATION.getName());
        JLabel bar3Name = new JLabel(AmenityCategory.SHOP.getName());
        JLabel bar4Name = new JLabel(AmenityCategory.ENTERTAINMENT.getName());
        JLabel bar5Name = new JLabel(AmenityCategory.TOURISM.getName());
        JLabel bar6Name = new JLabel(AmenityCategory.PUBLIC_SERVICES.getName());
        JLabel bar7Name = new JLabel(AmenityCategory.TRANSPORTATION.getName());

        // Create the labels for indexes
        indexes[0] = new JLabel();
        indexes[1] = new JLabel();
        indexes[2] = new JLabel();
        indexes[3] = new JLabel();
        indexes[4] = new JLabel();
        indexes[5] = new JLabel();
        indexes[6] = new JLabel();

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(bar1Name)
                                .addComponent(bar2Name)
                                .addComponent(bar3Name)
                                .addComponent(bar4Name)
                                .addComponent(bar5Name)
                                .addComponent(bar6Name)
                                .addComponent(bar7Name))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(bar1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bar2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bar3, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bar4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bar5, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bar6, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(bar7, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(indexes[0])
                                .addComponent(indexes[1])
                                .addComponent(indexes[2])
                                .addComponent(indexes[3])
                                .addComponent(indexes[4])
                                .addComponent(indexes[5])
                                .addComponent(indexes[6]))
                        .addContainerGap()
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar1Name)
                                .addComponent(bar1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[0]))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar2Name)
                                .addComponent(bar2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[1]))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar3Name)
                                .addComponent(bar3, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[2]))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar4Name)
                                .addComponent(bar4, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[3]))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar5Name)
                                .addComponent(bar5, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[4]))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar6Name)
                                .addComponent(bar6, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[5]))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(bar7Name)
                                .addComponent(bar7, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(indexes[6]))
                        .addContainerGap()
        );
    }

    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension(mainWidth / 3, 2 * mainHeight / 3));
        components.forEach(c -> c.changeSize((int) this.getPreferredSize().getWidth(), (int) this.getPreferredSize().getHeight()));
    }

    public void updateResults(List<Double> indexes){
        for (int i = 0; i < 7; i++) {
            ((AnimatedBar)components.get(i)).changeValue((indexes.get(i)).intValue());
            this.indexes[i].setText(indexes.get(i).toString() + "%");
        }
    }
}
