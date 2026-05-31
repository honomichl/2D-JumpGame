package Game;

import java.awt.*;

/**
 * Holds global configuration settings for the game, such as the player's
 * username and the selected visual theme. It also determines the matching
 * background and text colors based on the active theme.
 *
 * @author Filip Honomichl
 */
public class AppSettings {
    private static String username = "Player";
    private static String theme = "Dark";

    /**
     * Returns the appropriate background color depending on whether
     * the dark theme or light theme is active.
     */
    public static Color getBackgroundColor() {
        if ("Dark".equals(theme)) {
            return new Color(30, 30, 30);
        }
        return Color.WHITE;
    }

    /**
     * Returns the matching text color (foreground) that stands out
     * against the current background theme.
     */
    public static Color getForegroundColor() {
        if ("Dark".equals(theme)) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    /** Getters */
    public static String getUsername() {
        return username;
    }
    public static String getTheme() {
        return theme;
    }

    /** Setters */
    public static void setUsername(String username) {
        AppSettings.username = username;
    }
    public static void setTheme(String theme) {
        AppSettings.theme = theme;
    }
}