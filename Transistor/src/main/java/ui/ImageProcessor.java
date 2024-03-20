package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    private BufferedImage image;

    double ratio = 1.23;

    int targetWidth = 700;
    int targetHeight = (int) (700 * ratio);

    public BufferedImage readAndResize(String filePath) {
        BufferedImage outputImage = null;
        try {
            image = ImageIO.read(new File(filePath));

            int scalingHint = Image.SCALE_SMOOTH; // Or other scaling options

            Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, scalingHint);

            outputImage = new BufferedImage(targetWidth, targetHeight, image.getType());
            outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);

            ImageIO.write(outputImage, "jpg", new File("output.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return outputImage;
    }

    public int getTargetWidth(){
        return targetWidth;
    }

    public int getTargetHeight(){
        return targetHeight;
    }
}