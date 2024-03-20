package ui;

import javax.swing.*;
import java.awt.*;

public class ImageHolder extends JLayeredPane {
    private ImageProcessor imageProcessor = new ImageProcessor();
    int imageWidth = imageProcessor.getTargetWidth();
    int imageHeight= imageProcessor.getTargetHeight();

    int plottingAreaWidth = 490;
    int plottingAreaHeight = 690;
    public ImageHolder() {
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setBounds(0,0,imageWidth + 50,imageHeight + 50);
        this.add(imagePanel, Integer.valueOf(0));
        JPanel plottingPanel = new PlottingPanel(plottingAreaWidth,plottingAreaHeight);
        this.add(plottingPanel,  Integer.valueOf(1));
        plottingPanel.setBounds(0,0,plottingAreaWidth + 150, plottingAreaHeight + 125);
    }

}
