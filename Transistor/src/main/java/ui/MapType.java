package ui;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapType extends JComboBox<String> {
    private JXMapViewer jXMapViewer;
    public MapType(JXMapViewer jXMapViewer){
        this.jXMapViewer = jXMapViewer;
        this.setModel(new DefaultComboBoxModel<>(new String[] { "Open Stree", "Virtual Earth", "Hybrid", "Satellite" }));
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comboMapTypeActionPerformed(evt);
            }
        });
    }

    private void comboMapTypeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comboMapTypeActionPerformed
        TileFactoryInfo info;
        int index = this.getSelectedIndex();
        if (index == 0) {
            info = new OSMTileFactoryInfo();
        } else if (index == 1) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        } else if (index == 2) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        } else {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        }
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
    }
}
