package Game.GameObjects;

import Game.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Block {
    private final int x;
    private final int y;
    private final int size = 40;
    private final Rectangle hitbox;
    private BufferedImage img;


    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, size, size);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/block.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

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