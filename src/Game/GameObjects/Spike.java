package Game.GameObjects;

import Game.*;
import java.awt.*;

public class Spike {
    private int x;
    private int y;
    private final int size = 40;

    public Spike(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;

        /** left corner, middle, right corner */
        int[] xPoints = {screenX, screenX + (size / 2), screenX + size};
        /** left corner down, middle up, right corner down */
        int[] yPoints = {y + size, y, y + size};



        /** fill */
        g2d.setColor(Color.RED);
        g2d.fillPolygon(xPoints, yPoints, 3);

        /** outline */
        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawPolygon(xPoints, yPoints, 3);
    }

    /** hitbox */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }
}