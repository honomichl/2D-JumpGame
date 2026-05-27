package Game.GameObjects;

import Game.*;
import java.awt.*;

public class Block {
    private final int x;
    private final int y;
    private final int size = 40;
    private final Rectangle hitbox;


    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, size, size);
    }

    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;

        /** fill */
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(screenX, y, size, size);

        /** outline */
        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawRect(screenX, y, size, size);
    }

    /** getters */
    public int getY() {
        return y;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
}