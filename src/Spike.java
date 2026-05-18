import java.awt.*;

public class Spike {
    // Fixní pozice v herním světě
    private int x;
    private final int y = 400; // Stojí na podlaze (450 - 50 výška spiku)
    private final int width = 400; // Šířka základny
    private final int height = 50;

    public Spike(int x) {
        this.x = x;
    }

    // Vykreslení spiku s ohledem na posun kamery
    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;

        // Vykreslíme spike jen pokud je vidět na obrazovce (optimalizace)
        if (screenX > -50 && screenX < 850) {
            int[] xPoints = {screenX, screenX + 25, screenX + 50};
            int[] yPoints = {y + 50, y, y + 50};

            // Vnitřek trojúhelníku (červený jako nebezpečí)
            g2d.setColor(Color.RED);
            g2d.fillPolygon(xPoints, yPoints, 3);

            // Obrys spiku podle tématu (Light/Dark)
            g2d.setColor(AppSettings.getForegroundColor());
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    // Vrátí hitbox spiku jako Polygon pro přesnou detekci kolize trojúhelníku
    public Polygon getHitbox() {
        int[] xPoints = {x, x + 25, x + 50};
        int[] yPoints = {y + 50, y, y + 50};
        return new Polygon(xPoints, yPoints, 3);
    }
}