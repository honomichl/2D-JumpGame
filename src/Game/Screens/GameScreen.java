package Game.Screens;

import Game.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Main game screen panel that acts as a wrapper for the core gameplay area.
 * It manages HUD overlay, game layering, and inputs for jumping or pausing.
 *
 * @author Filip Honomichl
 */
public class GameScreen extends JPanel {

    private final GamePanel gamePanel = new GamePanel();
    private final JPanel pauseMenu = new JPanel();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final ScreenManager screenManager;

    private final JLabel progressLabel = UIFactory.createLabel("");
    private final JLabel attemptsLabel = UIFactory.createLabel("");

    /**
     * Constructor that sets up the layered window structure, displays HUD statistics,
     * hooks game events to the user interface, and initializes input controls.
     */
    public GameScreen(ScreenManager screen) {
        this.screenManager = screen;

        gamePanel.setOnWin(screenManager::showVictoryScreen);

        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);

        progressLabel.setForeground(Color.WHITE);
        attemptsLabel.setForeground(Color.WHITE);

        JPanel topPanel = UIFactory.createBoxLayout();
        topPanel.setOpaque(false);
        topPanel.add(progressLabel);
        topPanel.add(attemptsLabel);

        JPanel hudPanel = new JPanel(new BorderLayout());
        hudPanel.setOpaque(false);
        hudPanel.setBounds(0, 0, 800, 600);
        hudPanel.add(topPanel, BorderLayout.NORTH);

        gamePanel.setBounds(0, 0, 800, 600);
        pauseMenu.setBounds(0, 0, 800, 600);

        initPauseMenu();

        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(hudPanel,  JLayeredPane.MODAL_LAYER);

        gamePanel.getTimer().addActionListener(e -> {
            progressLabel.setText("Progress: " + (int)(gamePanel.getProgress() * 100) + "%");
            attemptsLabel.setText("Attempt: " + gamePanel.getAttempts());
        });

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

    /**
     * Toggles the gameplay pause status and alters the screen view
     * so that the game behind is less visible and stopped.
     */
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

    /**
     * Builds and styles the layout of the pause overlay component,
     * preparing buttons for continuing, resetting, or navigating back.
     */
    private void initPauseMenu() {
        pauseMenu.setLayout(new BorderLayout());
        pauseMenu.setOpaque(false);
        pauseMenu.setFocusable(true);

        /** center panel */
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel titleLabel = UIFactory.createTitle("Pause menu");
        titleLabel.setForeground(Color.WHITE);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton resumeButton = UIFactory.createButton("Resume");
        resumeButton.setFocusable(true);

        JButton restartButton = UIFactory.createButton("Reset level");
        restartButton.setFocusable(true);

        JButton menuButton = UIFactory.createButton("Menu");
        menuButton.setFocusable(true);

        resumeButton.addActionListener(e -> togglePauseScreen());
        restartButton.addActionListener(e -> {
            gamePanel.resetGameValues();
            togglePauseScreen();
        });
        menuButton.addActionListener(e -> {
            gamePanel.resetGameValues();
            togglePauseScreen();
            screenManager.showWelcomeScreen();
        });

        centerPanel.add(Box.createVerticalStrut(60));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(60));
        centerPanel.add(resumeButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(restartButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(menuButton);
        centerPanel.add(Box.createVerticalGlue());

        pauseMenu.add(centerPanel, BorderLayout.CENTER);
    }
}