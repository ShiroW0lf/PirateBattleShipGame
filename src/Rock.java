package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Rock {
    private int x, y;
    private int width, height;

    public Rock(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);  // Draw the rock
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
