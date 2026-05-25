package Game;

import java.awt.*;

/**
 * Reprezentuje žlutou kostku (hráče), její stav, fyziku pohybu a skákání.
 */
public class Player {

    // Pozice a rozměry hráče (Pevné měřítko 40x40 px)
    private final int x;
    private int y;
    private final int size = 40;

    // Fyzikální konstanty a proměnné
    private final int GRAVITY = 1;
    private final double JUMP_FORCE = -14;
    private double jumpSpeed = 0;
    private boolean onGround = true;

    /**
     * Konstruktor hráče.
     * @param startX Fixní pozice X na obrazovce (např. 150)
     * @param startY Počáteční výška načtená z JSONu
     */
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    /**
     * Vyvolá skok, pokud se hráč nachází na zemi.
     */
    public void jump() {
        if (onGround) {
            this.jumpSpeed = JUMP_FORCE;
            this.onGround = false;
        }
    }

    /**
     * Aktualizuje pohyb hráče v každém herním ticku (aplikuje gravitaci).
     */
    public void updateMovement() {
        this.jumpSpeed += GRAVITY;
        this.y += this.jumpSpeed;
    }

    /**
     * Resetuje stav hráče na výchozí hodnoty při úmrtí.
     * @param defaultY Výchozí bezpečná výška (např. 360)
     */
    public void reset(int defaultY) {
        this.y = defaultY;
        this.jumpSpeed = 0;
        this.onGround = true;
    }

    /**
     * Vykreslí žlutou kostku hráče a její černý obrys.
     */
    public void draw(Graphics2D g2d) {
        // Žlutá výplň
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(x, y, size, size);

        // Černý obrys podle nastavení hry
        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawRect(x, y, size, size);
    }

    /**
     * Vrátí aktuální hitbox hráče na obrazovce (pro zelené kreslení i kolize).
     */
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }

    // ==========================================
    //  GETTERY A SETTERY
    // ==========================================

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

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public int getSize() {
        return size;
    }
}