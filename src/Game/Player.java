package Game;

import java.awt.*;

/**
 * Reprezentuje žlutou kostku (hráče), její stav, fyziku pohybu a skákání.
 */
public class Player {
    private static final int gravity = 1;
    private static final double JUMP_FORCE = -14;
    private static final int size = 40;

    private final int offset = 13;
    private final Rectangle bigHitbox;
    private final Rectangle smallHitbox;

    private final int startX;
    private final int startY;

    private int x;
    private int y;
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

        this.bigHitbox = new Rectangle(startX, startY, size, size);
        this.smallHitbox = new Rectangle(startX + offset, startY + offset, size - (offset*2), size - (offset*2));
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
        this.jumpSpeed += gravity;
        this.y += this.jumpSpeed;

        this.bigHitbox.y = this.y;
        this.smallHitbox.y = this.y + offset;
    }

    /**
     * Resets player attributes
     */
    public void reset() {
        this.y = startY;
        this.x = startX;
        this.jumpSpeed = 0;
        this.onGround = false;
        this.bigHitbox.y = this.startY;
        this.smallHitbox.y = this.startY;
    }

    /**
     * draws a player
     */
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.MAGENTA);
        g2d.fillRect(x, y, size, size);

        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawRect(x, y, size, size);
    }

    /** Getters */
    public Rectangle getBigHitbox() {
        return bigHitbox;
    }
    public Rectangle getSmallHitbox() {
        return smallHitbox;
    }
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