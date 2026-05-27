package Game.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Objekt označující konec levelu. 
 * Je obrovský, sahá až nahoru a táhne se doprava.
 */
public class End {
    private final int x;
    private final int y = 0;
    private final int width = 3000;
    private final int height = 800;
    private final Rectangle hitBox;


    public End(int x) {
        this.x = x;
        this.hitBox = new Rectangle(x, y, width, height);
    }

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