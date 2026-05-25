package Game.GameObjects;

import java.awt.*;


public class Floor {

    private int x;
    private int y;
    private final int width = 40;
    private final int height = 40;


    public Floor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d, int cameraX) {


        g2d.setColor(Color.CYAN);
        g2d.fillRect(x - cameraX, y, width, height);

        g2d.setColor(Color.BLUE);
        g2d.drawRect(x - cameraX, y, width, height);
    }

    /** hitbox */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    /** getters */
    public int getY() {
        return y;
    }
}