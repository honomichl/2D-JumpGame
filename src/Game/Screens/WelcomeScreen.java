package Game.Screens;

import Game.*;
import javax.swing.*;
import java.awt.*;

//TODO zhezcit

// Změna 1: Třída je teď JPanel, ne samostatné okno
public class WelcomeScreen extends JPanel {

    private ScreenManager screen;

    // Změna 2: Konstruktor přijímá ScreenManager
    public WelcomeScreen(ScreenManager screen){
        this.screen = screen;

        // Změna 3: Smazán JFrame, konfigurujeme rovnou tento panel
        setLayout(new BorderLayout());

        // HORNÍ PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(AppSettings.getBackgroundColor());

        JLabel label = new JLabel("Welcome " + AppSettings.getUsername());
        label.setFont(new Font("Serif", Font.BOLD, 30));
        label.setForeground(AppSettings.getForegroundColor());

        topPanel.add(label);

        // Přidáváme na this panel
        add(topPanel, BorderLayout.NORTH);

        // STŘED (MENU)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(AppSettings.getBackgroundColor());

        /* start */
        JButton startButton = new JButton("Start");

        startButton.setFont(new Font("Serif", Font.ITALIC, 30));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 60));

        startButton.setForeground(AppSettings.getForegroundColor());
        startButton.setBackground(AppSettings.getBackgroundColor());
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            // Změna 4: Spuštění hry přes ScreenManager bez otevírání nového okna
            screen.showGameScreen();
        });

        /* settings */
        JButton settingsButton = new JButton("Settings");

        settingsButton.setFont(new Font("Serif", Font.PLAIN, 30));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setMaximumSize(new Dimension(200, 60));

        settingsButton.setForeground(AppSettings.getForegroundColor());
        settingsButton.setBackground(AppSettings.getBackgroundColor());
        settingsButton.setFocusPainted(false);

        settingsButton.addActionListener(e -> {
            // Změna 5: Přepnutí do nastavení přes ScreenManager
            screen.showSettingsScreen();
        });

        /* exit */
        JButton exitButton = new JButton("Exit");

        exitButton.setFont(new Font("Serif", Font.PLAIN, 30));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 60));

        exitButton.setForeground(AppSettings.getForegroundColor());
        exitButton.setBackground(AppSettings.getBackgroundColor());
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            // Změna 6: Vypnutí celé aplikace, jelikož okno spravuje manager
            System.exit(0);
        });

        /* mezery */
        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(settingsButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);

        // Přidáváme na this panel
        add(centerPanel, BorderLayout.CENTER);

        // SPODNÍ PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(AppSettings.getBackgroundColor());
        JLabel footer = new JLabel("© 2026 moje apka");
        bottomPanel.add(footer);

        // Změna 7: Nastavení pozadí rovnou tomuto panelu
        setBackground(AppSettings.getBackgroundColor());

        // Přidáváme na this panel
        add(bottomPanel, BorderLayout.SOUTH);
    }
}