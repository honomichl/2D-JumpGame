package Game.Screens;

import Game.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Hlavní obrazovka levelu. Funguje jako obal pro GamePanel (samotnou hru)
 * a stará se o zachytávání vstupů z klávesnice.
 */
public class Level extends JPanel {

    private GamePanel gamePanel;

    public Level(JFrame frame) {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        gamePanel = new GamePanel();
        this.add(gamePanel, BorderLayout.CENTER);

        // skakani
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