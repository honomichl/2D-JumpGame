package Game;

import java.awt.*;
import java.util.Random;

/**
 * Represents a single colorful square that flies off when the player dies.
 * It handles its own movement, simulated gravity, and slowly fades away.
 *
 * @author Filip Honomichl
 */
public class DeathParticle {
    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private int life = 40;
    private static Random rnd = new Random();

    /**
     * Creates a particle at a slightly randomized position around the player
     * and shoots it out in a random upward direction.
     */
    public DeathParticle(int startX, int startY) {
        x = startX + rnd.nextInt(40);
        y = startY + rnd.nextInt(40);
        speedX = (rnd.nextDouble() - 0.5) * 10;
        speedY = rnd.nextDouble() * -8;
    }

    /**
     * Moves the particle based on its speed, applies a bit of gravity
     * pulling it down, and shortens its remaining lifespan.
     */
    public void update() {
        x += speedX;
        y += speedY;
        speedY += 0.5;
        life--;
    }

    /**
     * Draws the small magenta particle as a simple square on the screen.
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect((int)x, (int)y, 8, 8);
    }

    /**
     * Checks if the particle has run out of juice and should be deleted.
     */
    public boolean isDone() {
        return life <= 0;
    }
}