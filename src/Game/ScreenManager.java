package Game;

import Game.Screens.*;
import Game.Screens.GameScreen;
import Game.Screens.WelcomeScreen;
import Game.Screens.SettingsScreen;
import javax.swing.*;

/**
 * The main window manager for the application. It controls the game window
 * frame and handles switching between different panels and menus.
 *
 * @author Filip Honomichl
 */
public class ScreenManager {
    private JFrame window;

    /** creates the screen in which other screens will be projected */
    public ScreenManager() {
        window = new JFrame("2D_JumpGame");
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
        GameScreen screen = new GameScreen(this);
        changeScreen(screen);

        screen.requestFocusInWindow();
    }

    /**
     * Swaps the current view with the victory panel and makes it focused.
     */
    public void showVictoryScreen() {
        VictoryScreen screen = new VictoryScreen(this);
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