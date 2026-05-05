import javax.swing.*;
import java.awt.*;

public class Settings {

    private JFrame frame;

    public Settings() {

        frame = new JFrame("Settings");

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // HORNÍ PANEL
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Settings");
        label.setFont(new Font("Serif", Font.BOLD, 30));
        topPanel.add(label);

        frame.add(topPanel, BorderLayout.NORTH);

        // STŘED
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // USERNAME
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(200, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // THEME
        JLabel themeLabel = new JLabel("Theme:");
        themeLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        themeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] themes = {"Light", "Dark"};
        JComboBox<String> themeBox = new JComboBox<>(themes);
        themeBox.setMaximumSize(new Dimension(200, 30));
        themeBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // NAČTENÍ AKTUÁLNÍCH HODNOT
        usernameField.setText(AppSettings.getUsername());
        themeBox.setSelectedItem(AppSettings.getTheme());

        // SAVE BUTTON
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Serif", Font.PLAIN, 25));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setMaximumSize(new Dimension(200, 60));

        saveButton.addActionListener(e -> {

            AppSettings.setUsername(usernameField.getText());
            AppSettings.setTheme((String) themeBox.getSelectedItem());

            System.out.println("Settings uložené:");
            System.out.println(AppSettings.getUsername() + " | " + AppSettings.getTheme());

            new WelcomeScreen();
            frame.dispose();
        });

        // BACK BUTTON
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Serif", Font.PLAIN, 25));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 60));

        backButton.addActionListener(e -> {
            new WelcomeScreen();
            frame.dispose();
        });

        // LAYOUT
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(usernameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(usernameField);

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(themeLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(themeBox);

        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(saveButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(backButton);

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}