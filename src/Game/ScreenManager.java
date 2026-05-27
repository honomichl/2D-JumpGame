package Game;

import Game.Screens.*;
import Game.Screens.GameScreen;
import Game.Screens.WelcomeScreen;
import Game.Screens.SettingsScreen; // Předpokládám tento balíček
import javax.swing.*;

/**
 * Hlavní manažer aplikace. Řídí jedno okno a přepíná v něm scény.
 */
public class ScreenManager {
    private JFrame window;

    /** creates the screen in which other screens will be projected */
    public ScreenManager() {
        // 1. Okno se vytvoří POUZE JEDNOU při startu
        window = new JFrame("Geometry Dash Clone");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        showWelcomeScreen();
        window.setVisible(true);
    }

    /** shows welcome screen */
    public void showWelcomeScreen() {
        changeScreen(new WelcomeScreen(this));
    }

    /** shows Settings */
    public void showSettingsScreen() {
        changeScreen(new SettingsScreen(this));
    }

    /** shows Game screen and makes it focused */
    public void showGameScreen() {
        GameScreen screen = new GameScreen();
        changeScreen(screen);

        screen.requestFocusInWindow();
    }

    /** process behind changing screens */
    private void changeScreen(JPanel newPanel) {
        window.getContentPane().removeAll();

        window.add(newPanel);

        window.revalidate();
        window.repaint();
    }
}