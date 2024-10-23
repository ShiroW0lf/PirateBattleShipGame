package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Boat {
    private int x, y;
    private int dx, dy;
    private final int WIDTH = 50;
    private final int HEIGHT = 30;
    
    private final int startX;
    private final int startY;
    
    // Speed factor for boat movement
    private final int speed = 2;

    public Boat(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        resetPosition();
    }

    public void resetPosition() {
        x = startX;
        y = startY;
        dx = 0;
        dy = 0;
    }

    public void updatePosition() {
        x += dx * speed;
        y += dy * speed;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19));  // Boat color - brown
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void setDirection(int key) {
        switch (key) {
            case KeyEvent.VK_LEFT:
                dx = -1;
                break;
            case KeyEvent.VK_UP:
                dy = -1;
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1;
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
                break;
        }
    }

    public void stopMoving() {
        dx = 0;
        dy = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
