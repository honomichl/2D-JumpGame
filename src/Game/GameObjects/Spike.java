package Game.GameObjects;

import Game.*;
import java.awt.*;

public class Spike {
    private final int x;
    private final int y;
    private final int size = 40;
    private final Polygon hitbox;


    public Spike(int x, int y) {
        this.x = x;
        this.y = y;

        int[] xPoints = {x, x + (size / 2), x + size};
        int[] yPoints = {y + size, y, y + size};
        this.hitbox = new Polygon(xPoints, yPoints, 3);
    }

    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;
        int[] drawX = {screenX, screenX + (size / 2), screenX + size};
        int[] drawY = {y + size, y, y + size};

        /** fill */
        g2d.setColor(Color.RED);
        g2d.fillPolygon(drawX, drawY, 3);

        /** outline */
        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawPolygon(drawX, drawY, 3);
    }

    /** Getters */
    public Polygon getHitbox() {
        return hitbox;
    }
}