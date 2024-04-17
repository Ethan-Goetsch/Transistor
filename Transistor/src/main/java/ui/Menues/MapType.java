package ui.Menues;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapType extends JMenu  {
    private JXMapViewer jXMapViewer;

    public MapType(JXMapViewer jXMapViewer){
        this.jXMapViewer = jXMapViewer;
        this.setText("Map Type");
        JMenuItem it1 = new JMenuItem("Default");
        JMenuItem it2 = new JMenuItem("Virtual Earth");
        JMenuItem it3 = new JMenuItem("Hybrid");
        JMenuItem it4 = new JMenuItem("Satellite");
        addActions(new JMenuItem[]{it1,it2,it3,it4});
        this.add(it1);
        this.addSeparator();
        this.add(it2);
        this.add(it3);
        this.add(it4);

    }

    private void addActions(JMenuItem[] items) {

        items[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMap(new OSMTileFactoryInfo());
            }
        });
        items[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMap(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP));
            }
        });
        items[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMap(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID));
            }
        });
        items[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeMap(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE));
            }
        });

    }
    private void changeMap(TileFactoryInfo info) {//GEN-FIRST:event_comboMapTypeActionPerformed
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
    }
}
