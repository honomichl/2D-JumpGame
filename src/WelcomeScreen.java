import javax.swing.*;
import java.awt.*;

public class WelcomeScreen {

    private JFrame frame;

    public WelcomeScreen(){
        frame = new JFrame("moje apka");

        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        // HORNÍ PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(AppSettings.getBackgroundColor());

        JLabel label = new JLabel("Welcome " + AppSettings.getUsername());
        label.setFont(new Font("Serif", Font.BOLD, 30));
        label.setForeground(AppSettings.getForegroundColor());

        topPanel.add(label);

        frame.add(topPanel, BorderLayout.NORTH);

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
            JFrame gameFrame = new JFrame("Geometry Dash Clone");
            Level gameLevel = new Level(gameFrame);
            gameFrame.add(gameLevel);
            gameFrame.setSize(800, 600);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            gameFrame.setResizable(false);
            frame.dispose(); // Zavře menu
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
            new Settings();
            frame.dispose();
        });

        /* exit */
        JButton exitButton = new JButton("Exit");

        exitButton.setFont(new Font("Serif", Font.PLAIN, 30));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 60));

        exitButton.setForeground(AppSettings.getForegroundColor());
        exitButton.setBackground(AppSettings.getBackgroundColor());
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> frame.dispose());

        /* mezery */
        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(settingsButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);

        frame.add(centerPanel, BorderLayout.CENTER);

        // SPODNÍ PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(AppSettings.getBackgroundColor());
        JLabel footer = new JLabel("© 2026 moje apka");
        bottomPanel.add(footer);
        frame.getContentPane().setBackground(AppSettings.getBackgroundColor());

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

