package Game.GameObjects;

import Game.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a standard static block object in the game world that the player
 * can land on or crash into. It handles its own positioning, hitbox, and texture.
 *
 * @author Filip Honomichl
 */
public class Block {
    private final int x;
    private final int y;
    private final int size = 40;
    private final Rectangle hitbox;
    private BufferedImage img;


    /**
     * Constructor that sets up the block's coordinates, creates its collision
     * hitbox, and attempts to load its visual sprite image.
     */
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, size, size);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/block.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Draws the block asset on the screen, shifting its horizontal position
     * based on the current camera movement to simulate scrolling.
     */
    public void draw(Graphics2D g2d, int cameraX) {
        if (img != null) {
            g2d.drawImage(img, x - cameraX, y, 40, 40, null);
        }
    }

    /** getters */
    public int getY() {
        return y;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
}