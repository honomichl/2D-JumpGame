package Game;

import Game.GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Samostatné herní plátno. Stará se o herní smyčku, logiku pohybu,
 * kolize a vykreslování samotné hry. Neřeší herní menu ani pauzu.
 */
public class GamePanel extends JPanel implements ActionListener {

    private Timer timer;
    private int cameraX = 0;
    private final int GAME_SPEED = 7;

    // Herní entity a objekty
    private Player player;
    private ArrayList<Spike> spikes;
    private ArrayList<Block> blocks;
    private ArrayList<Floor> floors;

    public GamePanel() {
        // Nastavení průhlednosti pozadí (barvu si můžeš brát z AppSettings)
        this.setBackground(AppSettings.getBackgroundColor());
        this.setFocusable(true);

        spikes = new ArrayList<>();
        blocks = new ArrayList<>();
        floors = new ArrayList<>();

        // Načtení řádků z JSONu přes tvůj Reader
        ArrayList<String> radky = LevelReader.loadLevelLines("/LevelLibrary.json");

        if (radky != null) {
            for (int r = 0; r < radky.size(); r++) {
                String radek = radky.get(r);

                for (int c = 0; c < radek.length(); c++) {
                    char znak = radek.charAt(c);
                    int x = c * 40;
                    int y = r * 40;

                    if (znak == 'P') {
                        this.player = new Player(150, y);
                    } else if (znak == 'b') {
                        blocks.add(new Block(x, y));
                    } else if (znak == 's') {
                        spikes.add(new Spike(x, y));
                    } else if (znak == '=') {
                        floors.add(new Floor(x, y));
                    }
                }
            }
        }

        // Pojistka, kdyby hráč v JSONu chyběl
        if (this.player == null) {
            this.player = new Player(150, 360);
        }

        // Nastartování herní smyčky (16ms = ~60 FPS)
        timer = new Timer(16, this);
        timer.start();
    }

    /**
     * Spustí nebo obnoví hru (vhodné pro zrušení pauzy).
     */
    public void startGame() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Zastaví hru (vhodné pro aktivaci pauzy).
     */
    public void stopGame() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Vrací informaci, zda hra momentálně běží (nebo je pozastavená).
     */
    public boolean isRunning() {
        return timer != null && timer.isRunning();
    }

    /**
     * Resetuje pozici hráče a kamery při úmrtí.
     */
    public void resetGameValues() {
        player.reset(360);
        cameraX = 0;
    }

    /**
     * Vyvolá skok hráče. Tuto metodu bude volat LevelScreen,
     * když zachytí stisknutí mezerníku.
     */
    public void makePlayerJump() {
        if (player != null) {
            player.jump();
        }
    }

    /* LOGIKA HRY – TIKÁNÍ TIMERU */
    @Override
    public void actionPerformed(ActionEvent e) {
        player.updateMovement();
        cameraX += GAME_SPEED;

        // Kontrola smrti (náraz do spiku nebo čelně do bloku)
        if (Collisions.checkDeathCollisions(player, spikes, blocks, cameraX)) {
            resetGameValues();
            // Tady bys mohl v budoucnu přes nějaký callback říct LevelScreenu,
            // aby navýšil pokusy (attempts++), které se kreslí v UI.
        }

        // Kontrola bezpečného přistání na blocích a podlahách
        Collisions.handleLanding(player, blocks, floors, cameraX);

        repaint();
    }

    /* VYKRESLOVÁNÍ HERNÍHO SVĚTA */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /* 1. Mřížka pozadí */
        g2d.setColor(new Color(128, 128, 128, 50));
        int gridSize = 40;
        int offsetX = cameraX % gridSize;

        for (int x = -offsetX; x < getWidth(); x += gridSize) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += gridSize) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        /* 2. Vykreslení herních objektů */
        for (Floor floor : floors) {
            floor.draw(g2d, cameraX);
        }
        for (Block block : blocks) {
            block.draw(g2d, cameraX);
        }
        for (Spike spike : spikes) {
            spike.draw(g2d, cameraX);
        }

        /* 3. Vykreslení hráče */
        player.draw(g2d);

        /* 4. Hitboxy pro ladění (zelené) */
        g2d.setColor(Color.GREEN);
        g2d.draw(player.getHitbox());

        g2d.translate(-cameraX, 0);
        for (Spike spike : spikes) {
            g2d.draw(spike.getHitbox());
        }
        for (Block block : blocks) {
            g2d.draw(block.getHitbox());
        }
        for (Floor floor : floors) {
            g2d.draw(floor.getHitbox());
        }
        g2d.translate(cameraX, 0);
    }
}