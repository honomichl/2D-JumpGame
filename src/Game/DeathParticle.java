package Game;

import java.awt.*;
import java.util.Random;

public class DeathParticle {
    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private int life = 40;
    private static Random rnd = new Random();

    public DeathParticle(int startX, int startY) {
        x = startX + rnd.nextInt(40);
        y = startY + rnd.nextInt(40);
        speedX = (rnd.nextDouble() - 0.5) * 10;
        speedY = rnd.nextDouble() * -8;
    }

    public void update() {
        x += speedX;
        y += speedY;
        speedY += 0.5;
        life--;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect((int)x, (int)y, 8, 8);
    }

    public boolean isDone() {
        return life <= 0;
    }
}