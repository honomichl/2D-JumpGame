package Game.Screens;

import Game.*;
import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JPanel {

    public WelcomeScreen(ScreenManager screen) {
        setLayout(new BorderLayout());
        setBackground(AppSettings.getBackgroundColor());

        /** top panel */
        JPanel topPanel = UIFactory.createFlowLayout();
        topPanel.add(UIFactory.createTitle("Welcome " + AppSettings.getUsername()));
        add(topPanel, BorderLayout.NORTH);

        /** center panel */
        JPanel centerPanel = UIFactory.createBoxLayout();

        JButton startButton    = UIFactory.createButton("Start");
        JButton settingsButton = UIFactory.createButton("Settings");
        JButton exitButton     = UIFactory.createButton("Exit");

        startButton.addActionListener(e -> screen.showGameScreen());
        settingsButton.addActionListener(e -> screen.showSettingsScreen());
        exitButton.addActionListener(e -> System.exit(0));

        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(settingsButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);
        add(centerPanel, BorderLayout.CENTER);

        /** down panel */
        JPanel bottomPanel = UIFactory.createFlowLayout();
        bottomPanel.add(new JLabel("© 2026 moje apka"));
        add(bottomPanel, BorderLayout.SOUTH);
    }
}