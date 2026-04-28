import javax.swing.*;
import java.awt.*;

public class WelcomeScreen {

    private JFrame frame;

    public WelcomeScreen(){
        frame = new JFrame("moje apka");
        init();
    }

    public void init(){

        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // HLAVNÍ LAYOUT
        frame.setLayout(new BorderLayout());

        // 🔹 HORNÍ PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Welcome");
        label.setFont(new Font("Serif", Font.BOLD, 30));
        topPanel.add(label);

        frame.add(topPanel, BorderLayout.NORTH);

        // 🔹 STŘED (MENU)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        /* start */
        JButton startButton = new JButton("Start");
        startButton.setForeground(Color.PINK);
        startButton.setBackground(new Color(9,9,9));
        startButton.setFont(new Font("Serif", Font.ITALIC, 30));
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 60));

        startButton.addActionListener(e -> {
            new Menu();
            frame.dispose();
        });

        /* settings */
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Serif", Font.PLAIN, 30));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setMaximumSize(new Dimension(200, 60));

        /* exit */
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.PLAIN, 30));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(200, 60));

        exitButton.addActionListener(e -> frame.dispose());

        /* mezery */
        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(settingsButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(exitButton);

        frame.add(centerPanel, BorderLayout.CENTER);

        // 🔹 SPODNÍ PANEL
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel footer = new JLabel("© 2026 moje apka");
        bottomPanel.add(footer);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

