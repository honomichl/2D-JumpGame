package Game.Screens;

import Game.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Hlavní obrazovka levelu. Funguje jako pasivní obal pro GamePanel.
 */
public class GameScreen extends JPanel {

    private final GamePanel gamePanel = new GamePanel();
    private final JPanel pauseMenu = new JPanel();
    private final JLayeredPane layeredPane = new JLayeredPane();

    public GameScreen() {
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
        gamePanel.setBounds(0, 0, 800, 600);
        pauseMenu.setBounds(0, 0, 800, 600);

        initPauseMenu();

        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !gamePanel.isPaused()) {
                    gamePanel.makePlayerJump();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    togglePauseScreen();
                }
            }
        });
    }

    private void togglePauseScreen() {
        gamePanel.togglePause();

        if (gamePanel.isPaused()) {
            layeredPane.add(pauseMenu, JLayeredPane.PALETTE_LAYER);
            pauseMenu.requestFocusInWindow();
        } else {
            layeredPane.remove(pauseMenu);
            this.requestFocusInWindow();
        }

        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void initPauseMenu() {
        pauseMenu.setLayout(new BorderLayout());
        pauseMenu.setOpaque(false);
        pauseMenu.setFocusable(true);

        /** top panel */
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        topPanel.add(UIFactory.createTitle("Pause menu"));

        pauseMenu.add(topPanel, BorderLayout.NORTH);

        /** center panel */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JButton resumeButton = UIFactory.createButton("Resume");
        resumeButton.setFocusable(true);

        JButton restartButton = UIFactory.createButton("Reset level");
        restartButton.setFocusable(true);

        resumeButton.addActionListener(e -> togglePauseScreen());
        restartButton.addActionListener(e -> {
            gamePanel.resetGameValues();
            togglePauseScreen();
        });

        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(resumeButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(restartButton);
        centerPanel.add(Box.createVerticalGlue());

        pauseMenu.add(centerPanel, BorderLayout.CENTER);
    }
}