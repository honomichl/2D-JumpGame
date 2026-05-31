package Game;

import Game.GameObjects.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Game canvas panel that drives the main game loop, movement physics,
 * collision processing, and direct gameplay rendering.
 *
 * @author Filip Honomichl
 */
public class GamePanel extends JPanel implements ActionListener {
    private boolean isPaused = false;
    private Runnable onWin;
    private Timer timer;
    private int cameraX = 0;
    private final int GAME_SPEED = 8;
    private int attempts = 1;
    private int levelEnd = 0;


    private Player player;
    private ArrayList<Spike> spikes;
    private ArrayList<Block> blocks;
    private ArrayList<Floor> floors;
    private End end;
    private BufferedImage background;

    ArrayList<DeathParticle> particles = new ArrayList<>();
    boolean dying = false;
    int dyingTimer = 0;

    /**
     * Constructor that loads the level layout configuration, loads the background textures,
     * sets up structural boundaries, and triggers the active loop timer.
     */
    public GamePanel() {
        this.setBackground(AppSettings.getBackgroundColor());
        this.setFocusable(true);

        LevelReader.loadLevel("/LevelLibrary.json", this);

        if (end != null) {
            levelEnd = end.getX();
        }

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/background.png"));
        } catch (IOException e) { e.printStackTrace(); }


        startGame();

    }

    /**
     * Core update cycle triggered by the timer event. Advances player kinetics, camera positioning,
     * evaluates death or victory states, and shows particle effects upon destruction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPaused) {
            return;
        }
        player.updateMovement();
        cameraX += GAME_SPEED;

        /** checks if player reached the end */
        if (Collisions.theEnd(player, this.end, cameraX)) {
            stopTimer();
            onWin.run();
            return;
        }

        /** checks for death */
        if (!dying && Collisions.checkDeathCollision(player, spikes, blocks, floors, cameraX)) {
            for (int i = 0; i < 15; i++) {
                particles.add(new DeathParticle(player.getX(), player.getY()));
            }
            dying = true;
            dyingTimer = 0;
        }

        if (dying) {
            for (int i = particles.size() - 1; i >= 0; i--) {
                particles.get(i).update();
                if (particles.get(i).isDone()){
                    particles.remove(i);
                }
            }
            dyingTimer++;
            if (dyingTimer > 35) {
                dying = false;
                particles.clear();
                resetGameValues();
            }
            repaint();
            return;
        }

        Collisions.handleLanding(player, blocks, floors, cameraX);

        repaint();
    }

    /**
     * Renders all visual elements onto the panel, tiling background assets,
     * painting static structures, active entities, and drawing death particles.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (background != null) {
            for (int bx = 0; bx < getWidth(); bx += 40) {
                for (int by = 0; by < getHeight(); by += 40) {
                    g2d.drawImage(background, bx, by, 40, 40, null);
                }
            }
        }

        /** game objects */
        for (Floor floor : floors) {
            floor.draw(g2d, cameraX);
        }
        for (Block block : blocks) {
            block.draw(g2d, cameraX);
        }
        for (Spike spike : spikes) {
            spike.draw(g2d, cameraX);
        }
        end.draw(g2d, cameraX);

        /** player */
        if (!dying) {
            player.draw(g2d);
        }

        for (DeathParticle p : particles) {
            p.draw(g2d);
        }

        /** background grid and hitboxes for future improvements */
/*
        g2d.setColor(new Color(128, 128, 128, 50));
        int gridSize = 40;
        int offsetX = cameraX % gridSize;

        for (int x = -offsetX; x < getWidth(); x += gridSize) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += gridSize) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        g2d.setColor(Color.GREEN);
        g2d.draw(player.getBigHitbox());

        g2d.setColor(Color.GREEN);
        g2d.draw(player.getSmallHitbox());

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
*/

        if (isPaused) {
            g2d.setColor(new Color(0,0,0,150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Pauses or unpauses the gameplay loop and refreshes the screen.     */
    public void togglePause() {
        this.isPaused = !this.isPaused;
        repaint();
    }

    /** creates and starts the timer */
    public void startGame() {
        timer = new Timer(16, this);
        timer.start();
    }


    /** starts a timer */
    public void startTimer() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    /** stops a timer */
    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    /** resets player position and camera */
    public void resetGameValues() {
        player.reset();
        cameraX = 0;
        attempts++;
    }

    /** calls for player to jump */
    public void makePlayerJump() {
        player.jump();
    }

    /** Calculates how much of the level the player has completed as a percentage decimal. */
    public float getProgress() {
        return (float) cameraX / levelEnd;
    }

    /** Getters */
    public int getAttempts() {
        return attempts;
    }
    public Timer getTimer() {
        return timer;
    }
    public int getCameraX() {
        return cameraX;
    }
    public int getGAME_SPEED() {
        return GAME_SPEED;
    }
    public Player getPlayer() {
        return player;
    }
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    public ArrayList<Floor> getFloors() {
        return floors;
    }
    public boolean isPaused() {
        return isPaused;
    }

    /** Setters */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setSpikes(ArrayList<Spike> spikes) {
        this.spikes = spikes;
    }
    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }
    public void setEnd(End end) {
        this.end = end;
    }
    public void setOnWin(Runnable onWin) {
        this.onWin = onWin;
    }
}