package ui.CustomComponents;

import javax.swing.border.AbstractBorder;
import java.awt.*;

class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }
}
