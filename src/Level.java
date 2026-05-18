import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level extends JPanel implements ActionListener {

    //TODO easy zmeny

    private Timer timer;
    private int playerY = 400; // Výška hráče
    private int jumpSpeed = 0; // Rychlost skoku
    private final int GRAVITY = 1;

    private int cameraX = 0;
    private final int GAME_SPEED = 5;
    private int attempts = 1;

    private ArrayList<Spike> spikes;
    private ArrayList<Block> blocks; // NOVÉ: Seznam bloků

    // Pomocná proměnná pro kontrolu, zda hráč stojí na nějakém objektu
    private boolean onGround = true;

    public Level(JFrame frame) {
        this.setBackground(AppSettings.getBackgroundColor());
        this.setFocusable(true);

        // Inicializace spiků
        spikes = new ArrayList<>();
        spikes.add(new Spike(600));
        spikes.add(new Spike(1300));
        spikes.add(new Spike(1350));

        // NOVÉ: Inicializace bloků (souřadnice X a Y)
        blocks = new ArrayList<>();
        // Výška 400 je úroveň země. Blok s Y=350 vytvoří schod vysoký 50px.
        blocks.add(new Block(900, 400));   // První blok na zemi
       // blocks.add(new Block(950, 350));   // Druhý blok (schůdek výš)
        blocks.add(new Block(1000, 350));  // Plošina na skákání
        blocks.add(new Block(1050, 350));  // Prodloužení plošiny

        // Spike umístěný na bloku pro extra obtížnost!
        spikes.add(new Spike(1000)); // Spike na souřadnici 1000 (bude stát na bloku)

        // Posluchač kláves (ovládání)
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Skočit lze pouze tehdy, pokud stojíme na zemi nebo na bloku
                if (e.getKeyCode() == KeyEvent.VK_SPACE && onGround) {
                    jumpSpeed = -15;
                    onGround = false;
                }
            }
        });

        // Game Loop
        timer = new Timer(16, this);
        timer.start();
    }

    private void resetLevel() {
        playerY = 400;
        jumpSpeed = 0;
        cameraX = 0;
        attempts++;
        onGround = true;
    }

    // KRESLENÍ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Podlaha
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 450, getWidth(), 5);

        // NOVÉ: Nakreslíme všechny bloky
        for (Block block : blocks) {
            block.draw(g2d, cameraX);
        }

        // Nakreslíme všechny spiky
        for (Spike spike : spikes) {
            spike.draw(g2d, cameraX);
        }

        // Nakreslíme hráče
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(100, playerY, 50, 50);

        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawRect(100, playerY, 50, 50);

        // Zobrazení pokusů
        g2d.setColor(AppSettings.getForegroundColor());
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Attempt " + attempts, 350, 50);
    }

    // LOGIKA POHYBU A KOLIZÍ
    @Override
    public void actionPerformed(ActionEvent e) {
        // Použijeme gravitaci
        jumpSpeed += GRAVITY;
        playerY += jumpSpeed;

        // Posun světa dopředu
        cameraX += GAME_SPEED;

        // Hitbox hráče v globálním světě
        Rectangle playerHitbox = new Rectangle(100 + cameraX, playerY, 50, 50);

        // 1. KONTROLA KOLIZE SE SPIKY (Smrt)
        for (Spike spike : spikes) {
            if (spike.getHitbox().intersects(playerHitbox)) {
                resetLevel();
                repaint();
                return; // Ukončíme metodu, hráč restartoval
            }
        }

        // 2. KONTROLA KOLIZÍ S BLOKY (Plošiny)
        onGround = false; // Předpokládáme, že padá, dokud neověříme opak

        // Základní kontrola pro podlahu (Y = 400)
        if (playerY >= 400) {
            playerY = 400;
            jumpSpeed = 0;
            onGround = true;
        }

        // Kontrola pro jednotlivé bloky
        for (Block block : blocks) {
            Rectangle blockBox = block.getHitbox();

            if (playerHitbox.intersects(blockBox)) {
                // Kontrola, zda padáme na blok SHORA
                // Hráčův spodek (playerY + 50) je blízko vršku bloku a zároveň hráč padá dolů (jumpSpeed >= 0)
                if ((playerY + 50) - jumpSpeed <= block.getY() + 10 && jumpSpeed >= 0) {
                    playerY = block.getY() - 50; // Umístíme hráče přesně na blok
                    jumpSpeed = 0;
                    onGround = true;
                }
                // Pokud do bloku narazíme Z BOKU (čelní náraz), v Geometry Dash to znamená smrt!
                else if (playerHitbox.x + 50 > blockBox.x && playerHitbox.x < blockBox.x + 10) {
                    resetLevel();
                    repaint();
                    return;
                }
            }
        }

        repaint();
    }
}