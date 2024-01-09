package org.example;

import javax.swing.*;
import java.awt.*;

class CustomSwingComponents extends JButton {
    public CustomSwingComponents(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.BLUE);
        setFont(new Font("Arial", Font.PLAIN, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getForeground());
        FontMetrics metrics = g.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = getHeight() / 2 + metrics.getDescent();
        g.drawString(getText(), x, y);
        g.drawLine(x, y + 3, x + metrics.stringWidth(getText()), y + 3);
    }

}