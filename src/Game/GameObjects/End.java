package Game.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the final trigger zone marking the end of the level.
 * It forms a massive vertical wall spanning from the finish line
 * all the way to the edge of the screen.
 *
 * @author Filip Honomichl
 */
public class End {
    private final int x;
    private final int y = 0;
    private final int width = 3000;
    private final int height = 800;
    private final Rectangle hitBox;


    /**
     * Constructor that defines the starting point of the finish line
     * and sets up its giant collision hitbox zone.
     */
    public End(int x) {
        this.x = x;
        this.hitBox = new Rectangle(x, y, width, height);
    }

    /**
     * Draws the solid finish zone on the screen, shifting its position
     * dynamically depending on how far the camera has scrolled forward.
     */
    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;

        /** fill */
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(screenX, y, width, height);
    }

    /** Getters */
    public Rectangle getHitbox() {
        return hitBox;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}