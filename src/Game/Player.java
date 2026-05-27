package Game;

import java.awt.*;

/**
 * Reprezentuje žlutou kostku (hráče), její stav, fyziku pohybu a skákání.
 */
public class Player {
    private int x;
    private final int startX;
    private int y;
    private final int startY;
    private final int size = 40;

    private final int GRAVITY = 1;
    private final double JUMP_FORCE = -14;
    private double jumpSpeed = 0;
    private boolean onGround = true;

    /**
     * constructor of player.
     * Sets starting cordinates and assignes them to the temporary ones.
     */
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.startX = startX;
        this.startY = startY;
    }

    /**
     * makes a player jump if he is on the ground
     */
    public void jump() {
        if (onGround) {
            this.jumpSpeed = JUMP_FORCE;
            this.onGround = false;
        }
    }

    /**
     * updates player speed on the Y axis
     */
    public void updateMovement() {
        this.jumpSpeed += GRAVITY;
        this.y += this.jumpSpeed;
    }

    /**
     * Resets player attributes
     */
    public void reset() {
        this.y = startY;
        this.x = startX;
        this.jumpSpeed = 0;
        this.onGround = false;
    }

    /**
     * Vykreslí žlutou kostku hráče a její černý obrys.
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(x, y, size, size);

        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawRect(x, y, size, size);
    }

    /**
     * returns hitbox of player
     */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }

    /** Getters */
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public double getJumpSpeed() {
        return jumpSpeed;
    }
    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }
    public boolean isOnGround() {
        return onGround;
    }
    public int getSize() {
        return size;
    }
    public double getJUMP_FORCE() {
        return JUMP_FORCE;
    }

    /** Setters */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}