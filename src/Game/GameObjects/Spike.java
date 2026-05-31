package Game.GameObjects;

import Game.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a dangerous triangular spike obstacle. If the player touches
 * its custom polygon hitbox, it triggers a crash and resets the level.
 *
 * @author Filip Honomichl
 */
public class Spike {
    private final int x;
    private final int y;
    private final int size = 40;
    private final Polygon hitbox;
    private BufferedImage img;

    /**
     * Constructor that defines the triangular collision points based on the
     * grid coordinates and loads the spike's visual sprite texture.
     */
    public Spike(int x, int y) {
        this.x = x;
        this.y = y;

        int[] xPoints = {x, x + (size / 2), x + size};
        int[] yPoints = {y + size, y, y + size};
        this.hitbox = new Polygon(xPoints, yPoints, 3);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/spike.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Draws the spike obstacle on the screen, moving it leftwards
     * matching the current camera scrolling position.
     */
    public void draw(Graphics2D g2d, int cameraX) {
        if (img != null) {
            g2d.drawImage(img, x - cameraX, y, 40, 40, null);
        }
    }

    /** Getters */
    public Polygon getHitbox() {
        return hitbox;
    }
}