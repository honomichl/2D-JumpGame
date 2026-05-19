import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level extends JPanel implements ActionListener {

    private Timer timer;
    /* zacinajici vyska hrace */
    private int playerY = 360;
    /* pozice hrace x */
    private final int playerX = 150;
    /* gravitace hrace(1 = klasika, -1 = vzhuru nohama) */
    private final int GRAVITY = 1;
    /* vyska skoku */
    private final double JUMP_FORCE = -14;
    /* rychlost hrace */
    private final int GAME_SPEED = 7;
    /* budouci pozice x */
    private double jumpSpeed = 0;
    /* kamera */
    private int cameraX = 0;
    /* pocet pokusu */
    private int attempts = 1;
    /* je na zemy? */
    private boolean onGround = true;

    private ArrayList<Spike> spikes;
    private ArrayList<Block> blocks;


    public Level(JFrame frame) {
        this.setBackground(AppSettings.getBackgroundColor());
        this.setFocusable(true);

        // Inicializace prázdných seznamů
        spikes = new ArrayList<>();
        blocks = new ArrayList<>();

        // JEDNODUCHÉ VOLÁNÍ: Předáme cestu k souboru, naše seznamy a klíčové slovo 'this' (aby mohl reader nastavit pozici hráče)
        LevelReader.loadLevel("/LevelLibrary.json", blocks, spikes, this);

        // Posluchač kláves...
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && onGround) {
                    jumpSpeed = JUMP_FORCE;
                    onGround = false;
                }
            }
        });

        timer = new Timer(16, this);
        timer.start();
    }

    /* reset levelu */
    private void resetLevel() {
        playerY = 360;
        jumpSpeed = 0;
        cameraX = 0;
        attempts++;
        onGround = true;
    }

    /* vykresleni */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* vykresleni mrizky */
        g2d.setColor(new Color(128, 128, 128, 50));
        int gridSize = 40;
        int offsetX = cameraX % gridSize;

        for (int x = -offsetX; x < getWidth(); x += gridSize) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += gridSize) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        /* vykresleni podlahy */
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 400, getWidth(), 50);

        // Kreslení bloků a spiků (Grafika)
        for (Block block : blocks) {
            block.draw(g2d, cameraX);
        }
        for (Spike spike : spikes) {
            spike.draw(g2d, cameraX);
        }

        // Kreslení hráče (Grafika)
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(playerX, playerY, 40, 40);

        g2d.setColor(AppSettings.getForegroundColor());
        g2d.drawRect(playerX, playerY, 40, 40);

        // =================================================================
        //  VYKRESLENÍ HITBOXŮ PRO LADĚNÍ (Zelená barva)
        // =================================================================
        g2d.setColor(Color.GREEN);

        // 1. Hitbox hráče
        g2d.drawRect(playerX, playerY, 40, 40);

        // Pomocná transformace posunu světa
        g2d.translate(-cameraX, 0);

        // 2. Hitboxy spiků
        for (Spike spike : spikes) {
            g2d.drawPolygon(spike.getHitbox());
        }

        // 3. Hitboxy bloků
        for (Block block : blocks) {
            g2d.draw(block.getHitbox());
        }

        // Vrátíme transformaci zpět pro správné vykreslení zbytku
        g2d.translate(cameraX, 0);
        // =================================================================

        // Zobrazení pokusů
        g2d.setColor(AppSettings.getForegroundColor());
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Attempt " + attempts, 350, 50);
    }

    // LOGIKA POHYBU A KOLIZÍ
    @Override
    public void actionPerformed(ActionEvent e) {
        jumpSpeed += GRAVITY;
        playerY += jumpSpeed;

        cameraX += GAME_SPEED;

        // Hitbox hráče v globálním světě
        Rectangle playerHitbox = new Rectangle(playerX + cameraX, playerY, 40, 40);

        // 1. Kontrola kolize se spiky
        for (Spike spike : spikes) {
            if (spike.getHitbox().intersects(playerHitbox)) {
                resetLevel();
                repaint();
                return;
            }
        }

        // 2. Kontrola kolizí s bloky
        onGround = false;

        if (playerY >= 360) {
            playerY = 360;
            jumpSpeed = 0;
            onGround = true;
        }

        for (Block block : blocks) {
            Rectangle blockBox = block.getHitbox();

            if (playerHitbox.intersects(blockBox)) {
                if ((playerY + 40) - jumpSpeed <= block.getY() + 10 && jumpSpeed >= 0) {
                    playerY = block.getY() - 40;
                    jumpSpeed = 0;
                    onGround = true;
                }
                else if (playerHitbox.x + 40 > blockBox.x && playerHitbox.x < blockBox.x + 10) {
                    resetLevel();
                    repaint();
                    return;
                }
            }
        }

        repaint();
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setSpikes(ArrayList<Spike> spikes) {
        this.spikes = spikes;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
}