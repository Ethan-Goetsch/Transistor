package ui.CustomComponents;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
/**
 * Factory class for creating circular icon buttons.
 */
public class CircularIconButtonFactory {
    public final int ICON_SIZE;

    public CircularIconButtonFactory(int size){
        ICON_SIZE = size;
    }
    public CircularIconButton createIconButton(String iconPath) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        ImageIcon iconCircular = createCircularIcon(iconScaled);
        return new CircularIconButton(iconCircular);
    }

    private ImageIcon createCircularIcon(Image originalIcon) {
        ImageIcon iconCircular = new ImageIcon(originalIcon);
        int diameter = Math.min(iconCircular.getIconWidth(), iconCircular.getIconHeight());
    
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        int x = (diameter - originalIcon.getWidth(null)) / 2;
        int y = (diameter - originalIcon.getHeight(null)) / 2;
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(originalIcon, x, y, diameter, diameter, null);
    
        g2.dispose();
        return new ImageIcon(image);
    }
}
