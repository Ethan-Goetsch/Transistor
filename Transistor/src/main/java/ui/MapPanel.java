package ui;

import org.jxmapviewer.JXMapViewer;

import java.awt.*;

import javax.swing.*;

public class MapPanel extends JPanel
{
        private JXMapViewer jXMapViewer;

        public MapPanel(int mainWidth, int mainHeight)
        {

                this.setPreferredSize(new Dimension(2 * mainWidth / 3, mainHeight));
                jXMapViewer = new JXMapViewer();
                jXMapViewer.setSize(new Dimension(mainWidth/3, mainHeight));
                initComponents();
        }
        
        private void initComponents(){
                MMap map = new MMap(jXMapViewer);
                MapType comboBox = new MapType(jXMapViewer);

                GroupLayout layout = new GroupLayout(this);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jXMapViewer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(30)
                                        .addComponent(comboBox)
                                        .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jXMapViewer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(comboBox)
                                        .addContainerGap())
                );
        }

}