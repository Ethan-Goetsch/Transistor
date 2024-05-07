package ui.CustomComponents;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ScrollBarUI extends BasicScrollBarUI {
    private final int BAR_SIZE = 60;

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }
    @Override
    protected Dimension getMaximumThumbSize() {
        return scrollbar.getOrientation() == JScrollBar.VERTICAL ? new Dimension(0, BAR_SIZE): new Dimension(BAR_SIZE,0);
    }

    @Override
    protected Dimension getMinimumThumbSize() {
        return scrollbar.getOrientation() == JScrollBar.VERTICAL ? new Dimension(0, BAR_SIZE): new Dimension(BAR_SIZE,0);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent jc, Rectangle rcg) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int orientation = scrollbar.getOrientation();
        int x, y, width, height;
        boolean isVertical = orientation == JScrollBar.VERTICAL;
        int size = isVertical ? rcg.width / 2 : rcg.height / 2;
        x = isVertical ? rcg.x + (rcg.width - size) / 2: rcg.x;
        y = isVertical ? rcg.y : rcg.y + (rcg.height - size) / 2;
        width = isVertical ? size : rcg.width;
        height = isVertical ? rcg.height : size;
        g2.setColor(new Color(240, 240, 240));
        g2.fillRect(x, y, width, height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent jc, Rectangle rcg){
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = rcg.x;
        int y = rcg.y;
        int width= rcg.width;
        int height = rcg.height;
        if(scrollbar.getOrientation() == JScrollBar.VERTICAL){
            y+=8;
            height-=16;
        }else{
            x+=8;
            width+=16;
        }
        g2.setColor(scrollbar.getForeground());
        g2.fillRoundRect(x,y,width,height,10,10);
    }
}
