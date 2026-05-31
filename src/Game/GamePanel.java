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
    private boolean isPaused = false;
    private Runnable onWin;
    private Timer timer;
    private int cameraX = 0;
    private final int GAME_SPEED = 7;
    private int attempts = 1;
    private int levelEnd = 0;

    private Player player;
    private ArrayList<Spike> spikes;
    private ArrayList<Block> blocks;
    private ArrayList<Floor> floors;
    private End end;

    public GamePanel() {
        this.setBackground(AppSettings.getBackgroundColor());
        this.setFocusable(true);

        boolean success = LevelReader.loadLevel("/LevelLibrary.json", this);

        if (end != null) {
            levelEnd = end.getX();
        }

        startGame();

    }

    /* LOGIKA HRY – TIKÁNÍ TIMERU */
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
        if (Collisions.checkDeathCollision(player, spikes, blocks, floors, cameraX)) {
            resetGameValues();
        }

        Collisions.handleLanding(player, blocks, floors, cameraX);

        repaint();
    }

    /* VYKRESLOVÁNÍ HERNÍHO SVĚTA */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
        player.draw(g2d);

        /** temporary background grid */
        g2d.setColor(new Color(128, 128, 128, 50));
        int gridSize = 40;
        int offsetX = cameraX % gridSize;

        for (int x = -offsetX; x < getWidth(); x += gridSize) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += gridSize) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        /** temporary hitboxes */
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

        if (isPaused) {
            g2d.setColor(new Color(0,0,0,150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

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