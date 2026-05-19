import java.awt.*;

public class Spike {
    private int x;
    private int y;
    private final int size = 40;


    public Spike(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Vizuální vykreslení zůstává VELKÉ, aby hra dobře vypadala
    public void draw(Graphics2D g2d, int cameraX) {
        int screenX = x - cameraX;

        int[] xPoints = {screenX, screenX + (size / 2), screenX + size};
        int[] yPoints = {y + size, y, y + size};

        g2d.setColor(Color.RED);
        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawPolygon(xPoints, yPoints, 3);
    }

    // NOVÉ: Hitbox je MENŠÍ než grafika (přesně jako v Geometry Dash)
    public Polygon getHitbox() {
        int paddingX = 15;  // O kolik pixelů bude hitbox užší zleva i zprava (celkem o 16px)
        int paddingY = 10; // O kolik pixelů bude hitbox nižší shora

        // Přepočítané body pro menší trojúhelník uvnitř spiku
        int[] xPoints = {
                x + paddingX,               // Levý dolní roh posunutý doprava
                x + (size / 2),            // Vrchol zůstává uprostřed
                x + size - paddingX        // Pravý dolní roh posunutý doleva
        };

        int[] yPoints = {
                y + size,                 // Spodek necháme na zemi
                y + paddingY,               // Vrchol posunutý dolů (nižší spike)
                y + size                  // Spodek necháme na zemi
        };

        return new Polygon(xPoints, yPoints, 3);
    }
}