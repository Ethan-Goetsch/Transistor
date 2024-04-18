package ui.CustomComponents;
import javax.swing.*;
import java.awt.*;
public class AnimatedLine extends JComponent {

    private Point start;
    private Point end;
    private Point startTarget;
    private Point endTarget;
    private Timer timer;

    public AnimatedLine(Point start, Point end) {
        this.start = start;
        this.end = end;
        timer = new Timer(5, e -> updateAndRepaint());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
    }

    public void moveTo(Point start, Point end) {
        startTarget = start;
        endTarget = end;
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    private void updateAndRepaint() {
        updatePoints(startTarget, endTarget);
        repaint();
        if (start.equals(end)) {
            timer.stop();
        }
    }


    private void updatePoints(Point targetStart, Point targetEnd) {
        int dx = Math.abs(targetStart.x - start.x);
        int dy = Math.abs(targetStart.y - start.y);

        int moveX = Math.min(10, dx) * sign(targetStart.x - start.x);
        int moveY = Math.min(10, dy) * sign(targetStart.y - start.y);

        start.translate(moveX, moveY);
        end.translate(moveX, moveY);
    }
    private int sign(int value) {
        return value < 0 ? -1 : 1;
    }
}
