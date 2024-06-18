package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedBar extends JPanel implements Resizible {
    private int currentValue = 0;
    private int targetValue;
    private Timer timer;

    public AnimatedBar(int targetValue, int mainWidth, int mainHeight) {
        this.targetValue = targetValue;
        this.setBackground(Color.white);
        changeSize(mainWidth, mainHeight);
        setupTimer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int padding = 5;
        int barHeight = 10;

        int availableWidth = getWidth() - 2 * padding;
        int barWidth = (int) (availableWidth * (currentValue / 100.0));

        // Color selection based on targetValue
        if(targetValue <= 25){
            g.setColor(Color.RED);
        }else if(targetValue <= 50){
            g.setColor(Color.YELLOW);
        }else{
            g.setColor(Color.GREEN);
        }

        // Draw the bar
        g.fillRoundRect(padding, padding, barWidth, barHeight, barHeight, barHeight);

        // Draw the border
        g.setColor(Color.BLACK);
        g.drawRoundRect(padding, padding, availableWidth, barHeight, barHeight, barHeight);
    }

    private void setupTimer() {
        if (timer != null) {
            timer.stop();
        }

        int delay = 10;
        int step = 1;

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentValue < targetValue) {
                    currentValue += Math.min(step, targetValue - currentValue);
                    repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void changeValue(int newValue) {
        if (newValue < 0 || newValue > 100) {
            throw new IllegalArgumentException("Value must be between 0 and 100.");
        }

        this.targetValue = newValue;
        this.currentValue = 0;
        setupTimer();
    }

    @Override
    public void changeSize(int mainWidth, int mainHeight) {
        setPreferredSize(new Dimension((int)(mainWidth / 2.5), 20));
        revalidate();
        repaint();
    }
}
