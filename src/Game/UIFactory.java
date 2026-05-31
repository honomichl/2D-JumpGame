package Game;

import javax.swing.*;
import java.awt.*;

/**
 * A class for creating styled UI components. It helps eliminate
 * repetitive code when setting up buttons, labels, and panels across the app.
 *
 * @author Filip Honomichl
 */
public class UIFactory {
    public static final Font FONT_TITLE    = new Font("Serif", Font.BOLD,   50);
    public static final Font FONT_BUTTON   = new Font("Serif", Font.PLAIN,  30);
    public static final Font FONT_LABEL    = new Font("Serif", Font.PLAIN,  20);

    public static final Dimension BUTTON_SIZE = new Dimension(200, 60);
    public static final Dimension FIELD_SIZE  = new Dimension(200, 30);


    /**
     * Creates and styles a standard menu button with correct fonts,
     * colors based on the theme, and uniform dimensions.
     */
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(BUTTON_SIZE);
        button.setForeground(AppSettings.getForegroundColor());
        button.setBackground(AppSettings.getBackgroundColor());
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Creates a large, prominent text label suitable for screen titles.
     */
    public static JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_TITLE);
        label.setForeground(AppSettings.getForegroundColor());
        return label;
    }

    /**
     * Creates a standard text label, centered and styled with the current theme colors.
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(AppSettings.getForegroundColor());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Creates a panel with a centered FlowLayout and matching background color.
     */
    public static JPanel createFlowLayout() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(AppSettings.getBackgroundColor());
        return panel;
    }

    /**
     * Creates a panel with a vertical BoxLayout stacked from top to bottom.
     */
    public static JPanel createBoxLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppSettings.getBackgroundColor());
        return panel;
    }
}