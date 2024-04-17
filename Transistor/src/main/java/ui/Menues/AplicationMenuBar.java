package ui.Menues;

import org.jxmapviewer.JXMapViewer;
import ui.SearchPanel;

import javax.swing.*;

public class AplicationMenuBar extends JMenuBar {
    private JXMapViewer jXMapViewer;
    private final SearchPanel searchPanel;

    public AplicationMenuBar(JXMapViewer jXMapViewer, SearchPanel searchPanel){
        this.jXMapViewer = jXMapViewer;
        this.searchPanel = searchPanel;
        initComponents();
    }

    private void initComponents(){
        Settings settingsMenu = new Settings(jXMapViewer, searchPanel);
        this.add(settingsMenu);
    }
}
