import java.awt.*;

public class Block {
    private int x;
    private int y; // Výška bloku (např. 400 je na zemi, 350 je ve vzduchu)
    private final int size = 50;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Vykreslení bloku s ohledem na posun kamery
    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;

        // Vykreslíme pouze pokud je vidět na obrazovce
        if (screenX > -size && screenX < 850) {
            // Výplň bloku (šedá/oranžová nebo podle libosti, dáme šedou)
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(screenX, y, size, size);

            // Obrys bloku podle tématu (Light/Dark)
            g2d.setColor(AppSettings.getForegroundColor());
            g2d.drawRect(screenX, y, size, size);
        }
    }

    // Hitbox pro detekci kolizí a stání na bloku
    public Rectangle getHitbox() {
        return new Rectangle(x, y, size, size);
    }

    public int getY() {
        return y;
    }
}