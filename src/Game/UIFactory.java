package Game;

import javax.swing.*;
import java.awt.*;

/**
 * Továrna na stylované UI komponenty.
 * Eliminuje opakující se kód při vytváření tlačítek, labelů a panelů.
 */
public class UIFactory {
    public static final Font FONT_TITLE    = new Font("Serif", Font.BOLD,   50);
    public static final Font FONT_BUTTON   = new Font("Serif", Font.PLAIN,  30);
    public static final Font FONT_LABEL    = new Font("Serif", Font.PLAIN,  20);

    public static final Dimension BUTTON_SIZE = new Dimension(200, 60);
    public static final Dimension FIELD_SIZE  = new Dimension(200, 30);


    /**
     * buttons
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
     * labels
     */
    public static JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_TITLE);
        label.setForeground(AppSettings.getForegroundColor());
        return label;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(AppSettings.getForegroundColor());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * layouts for panels
     */
    public static JPanel createFlowLayout() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(AppSettings.getBackgroundColor());
        return panel;
    }

    public static JPanel createBoxLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppSettings.getBackgroundColor());
        return panel;
    }
}