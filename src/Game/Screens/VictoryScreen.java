package Game.Screens;

import Game.*;
import javax.swing.*;
import java.awt.*;

public class VictoryScreen extends JPanel {

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

//        /** bottom panel */
//        JPanel bottomPanel = UIFactory.createFlowLayout();
//        bottomPanel.add(UIFactory.createLabel("© 2026 moje apka"));
//        add(bottomPanel, BorderLayout.SOUTH);
    }
}