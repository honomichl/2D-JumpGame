package Game.GameObjects;

import Game.*;
import java.awt.*;

public class Block {
    private int x;
    private int y; // Výška bloku (např. 400 je na zemi, 350 je ve vzduchu)
    private final int size = 40;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
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

    /** hitbox */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }

    /** getters */
    public int getY() {
        return y;
    }
}