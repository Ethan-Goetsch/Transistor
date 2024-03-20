package ui;

import javax.swing.*;
import java.awt.*;

public class ImageHolder extends JPanel {
    public ImageHolder() {
        ImagePanel imagePanel = new ImagePanel();
        this.setLayout(new BorderLayout());
        this.add(imagePanel, BorderLayout.CENTER);
    }

}
