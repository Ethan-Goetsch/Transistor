package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.nio.file.*;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private String filePath = "Transistor/src/main/resources/MaasMap.png";

    public ImagePanel() {
        ImageProcessor imageProcessor = new ImageProcessor();
        image = imageProcessor.readAndResize(filePath);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 50, 25, this);
    }

}
