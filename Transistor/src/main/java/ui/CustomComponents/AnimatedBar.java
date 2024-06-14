package ui.CustomComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
        if(targetValue <=25){
            g.setColor(Color.RED);
        }else if(targetValue <= 50){
            g.setColor(Color.yellow);
        }else{
            g.setColor(Color.GREEN);
        }

        int barWidth = (int) (getWidth() * (currentValue / 100.0));
        int barHeight = 10;
        int arcWidth = barHeight;
        int arcHeight = barHeight;

        g.fillRoundRect(5, 5, barWidth, barHeight, arcWidth, arcHeight);

        g.setColor(Color.BLACK);
        g.drawRoundRect(5, 5, getWidth() - 10, barHeight, arcWidth, arcHeight);
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
    }

}
