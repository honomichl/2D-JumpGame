package Game.Screens;

import Game.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Hlavní obrazovka levelu. Funguje jako pasivní obal pro GamePanel.
 */
public class GameScreen extends JPanel {

    private GamePanel gamePanel;

    public GameScreen() {
        setLayout(new BorderLayout());

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        /** reacts on space by making player jump */
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gamePanel.makePlayerJump();
                }
            }
        });
    }
}