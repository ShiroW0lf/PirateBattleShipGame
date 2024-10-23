package src;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Random;



public class GameCanvas extends JPanel {
    private int screenWidth = 800;
    private int screenHeight = 600;
    private ArrayList<Rock> rocks;
    private Boat boat;
    private boolean gameOver;  // Tracks if the game is over
    private boolean onTitleScreen = true;  // Title screen flag

    public GameCanvas() {
        rocks = new ArrayList<>();
        generateStaticRocks();  // Static rocks generated once
        boat = new Boat(100, screenHeight / 2);  // Start the boat in the middle
        gameOver = false;  // Game starts with gameOver set to false
        setUpMouseListener();  // Set up mouse listener for clicking "Start Game"
    }

    // Static map generation (only once when the game is initialized)
    public void generateStaticRocks() {
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {  // 15 static rocks placed
            int rockX = 150 + rand.nextInt(screenWidth - 200);  // Avoid right edge
            int rockY = 100 + rand.nextInt(screenHeight - 150);  // Avoid top and bottom edges
            int rockSize = 40 + rand.nextInt(40);
            rocks.add(new Rock(rockX, rockY, rockSize, rockSize));
        }
    }

    // Mouse listener for the "Start Game" button on the title screen
    private void setUpMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onTitleScreen) {
                    // Check if the "Start Game" button is clicked
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    // Button position
                    int buttonX = screenWidth / 2 - 100;
                    int buttonY = screenHeight / 2;
                    int buttonWidth = 200;
                    int buttonHeight = 50;

                    if (mouseX >= buttonX && mouseY >= buttonY && mouseX <= buttonX + buttonWidth && mouseY <= buttonY + buttonHeight) {
                        onTitleScreen = false;  // Transition from title screen to game
                        resetGame();  // Initialize the game when starting
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (onTitleScreen) {
            // Draw the title screen
            drawTitleScreen(g);
        } else if (gameOver) {
            // Draw game over screen if game over
            drawGameOverScreen(g);
        } else {
            // Draw the sea (map) and game elements if the game is ongoing
            drawGameElements(g);
        }
    }

    private void drawTitleScreen(Graphics g) {
        // Background
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Title text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("Pirate Battleship", screenWidth / 2 - 180, screenHeight / 3);

        // Draw "Start Game" button
        g.setColor(Color.BLACK);
        g.fillRect(screenWidth / 2 - 100, screenHeight / 2, 200, 50);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Start Game", screenWidth / 2 - 60, screenHeight / 2 + 35);
    }

    private void drawGameOverScreen(Graphics g) {
        // Draw the sea (map)
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Draw rocks
        for (Rock rock : rocks) {
            rock.draw(g);
        }

        // Draw the boat
        boat.draw(g);

        // Game Over text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", screenWidth / 2 - 120, screenHeight / 2 - 20);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press 'R' to Restart", screenWidth / 2 - 100, screenHeight / 2 + 20);
    }

    private void drawGameElements(Graphics g) {
        // Draw the sea (map)
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Draw rocks
        for (Rock rock : rocks) {
            rock.draw(g);
        }

        // Draw the boat
        boat.draw(g);
    }

    public void updateGame() {
        if (!onTitleScreen && !gameOver) {
            boat.updatePosition();  // Update boat position

            // Check for collisions with rocks
            for (Rock rock : rocks) {
                if (boat.getBounds().intersects(rock.getBounds())) {
                    // Trigger game over
                    gameOver = true;  // Set game over state
                    break;
                }
            }

            repaint();  // Repaint the screen after updating position
        }
    }

    // Method to restart the game
    public void resetGame() {
        boat.resetPosition();  // Reset boat to starting position
        gameOver = false;  // Reset the game over flag
        repaint();  // Repaint the screen to reflect the reset state
    }

    // Method to handle key press
    public void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_R && gameOver) {
            resetGame();  // Restart the game if 'R' is pressed
        } else if (!gameOver && !onTitleScreen) {
            // Let the boat move if the game is not over or on title screen
            boat.setDirection(keyCode);
        }
    }

    // Method to stop the boat's movement when a key is released
    public void handleKeyRelease(int keyCode) {
        if (!gameOver && !onTitleScreen) {
            boat.stopMoving();
        }
    }
}
