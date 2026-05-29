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
        pauseMenu.setLayout(new BoxLayout(pauseMenu, BoxLayout.Y_AXIS));
        pauseMenu.setOpaque(false);
        pauseMenu.setFocusable(true);

        JLabel pauseLabel = new JLabel("PAUSE");
        pauseLabel.setFont(new Font("Serif", Font.BOLD, 50));
        pauseLabel.setForeground(AppSettings.getForegroundColor());
        pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        /** resume button */
        JButton resumeButton = new JButton("resume");

        resumeButton.setFont(new Font("Serif", Font.PLAIN, 30));
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeButton.setMaximumSize(new Dimension(200, 60));

        resumeButton.setForeground(AppSettings.getForegroundColor());
        resumeButton.setBackground(AppSettings.getBackgroundColor());
        resumeButton.setFocusPainted(false);
        resumeButton.setFocusable(true);

        resumeButton.addActionListener(e -> {
            togglePauseScreen();
        });

        /** restart button */
        JButton restartButton = new JButton("reset level");

        restartButton.setFont(new Font("Serif", Font.PLAIN, 30));
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setMaximumSize(new Dimension(200, 60));

        restartButton.setForeground(AppSettings.getForegroundColor());
        restartButton.setBackground(AppSettings.getBackgroundColor());
        restartButton.setFocusPainted(false);
        restartButton.setFocusable(true);

        restartButton.addActionListener(e -> {
            gamePanel.resetGameValues();
            togglePauseScreen();
        });

        pauseMenu.add(Box.createVerticalGlue());
        pauseMenu.add(pauseLabel);
        pauseMenu.add(Box.createVerticalStrut(30));
        pauseMenu.add(resumeButton);
        pauseMenu.add(Box.createVerticalStrut(15));
        pauseMenu.add(restartButton);
        pauseMenu.add(Box.createVerticalGlue());
    }
}