package Game.Screens;

import Game.*;
import javax.swing.*;
import java.awt.*;

/**
 * The screen that pops up when a player successfully beats a level.
 * It shows a congratulatory message and gives options to replay,
 * go back to the main menu, or quit.
 *
 * @author Filip Honomichl
 */
public class VictoryScreen extends JPanel {

    /**
     * Sets up the layout, displays the victory title, and configures
     * the buttons for restarting, viewing the menu, or exiting.
     */
    public VictoryScreen(ScreenManager screen) {
        setLayout(new BorderLayout());
        setBackground(AppSettings.getBackgroundColor());

        /** top panel */
        JPanel topPanel = UIFactory.createFlowLayout();
        topPanel.add(UIFactory.createTitle("You won, Congratulations!"));
        add(topPanel, BorderLayout.NORTH);

        /** center panel */
        JPanel centerPanel = UIFactory.createBoxLayout();

        JButton playAgainButton = UIFactory.createButton("Play Again");
        JButton menuButton = UIFactory.createButton("Menu");
        JButton exitButton = UIFactory.createButton("Exit");

        playAgainButton.addActionListener(e -> screen.showGameScreen());
        menuButton.addActionListener(e -> screen.showWelcomeScreen());
        exitButton.addActionListener(e -> System.exit(0));

        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(playAgainButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(menuButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);
        add(centerPanel, BorderLayout.CENTER);

    }
}