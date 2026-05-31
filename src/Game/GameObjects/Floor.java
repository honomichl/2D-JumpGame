package Game.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a solid floor segment in the game world. It stretches vertically
 * downwards to prevent the player from falling out of bounds and provides
 * a stable surface to run on.
 *
 * @author Filip Honomichl
 */
public class Floor {
    private final int x;
    private final int y;
    private final int width = 40;
    private final int height = 400;
    private final Rectangle hitBox;
    private BufferedImage img;


    /**
     * Constructor that defines the floor's boundaries, positions its large vertical
     * collision hitbox, and loads the surface texture image.
     */
    public Floor(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitBox = new Rectangle(x, y, width, height);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/floor.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Draws the floor asset on the screen, applying a horizontal offset based
     * on the camera's position to handle smooth map scrolling.
     */
    public void draw(Graphics2D g2d, int cameraX) {
        if (img != null) {
            for (int tx = x; tx < x + width; tx += 40) {
                g2d.drawImage(img, tx - cameraX, y, width, height, null);
            }
        }
    }

    /** getters */
    public int getY() {
        return y;
    }
    public Rectangle getHitbox() {
        return hitBox;
    }
}