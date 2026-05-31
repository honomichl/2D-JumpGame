package Game.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Floor {
    private final int x;
    private final int y;
    private final int width = 40;
    private final int height = 400;
    private final Rectangle hitBox;
    private BufferedImage img;


    public Floor(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitBox = new Rectangle(x, y, width, height);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/floor.png"));
        } catch (IOException e) { e.printStackTrace(); }
    }

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