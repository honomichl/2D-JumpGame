import java.awt.*;

public class AppSettings {

    // výchozí hodnoty
    private static String username = "Player";
    private static String theme = "Light";

    public static Color getBackgroundColor() {
        if ("Dark".equals(theme)) {
            return new Color(30, 30, 30);
        }
        return Color.WHITE;
    }

    public static Color getForegroundColor() {
        if ("Dark".equals(theme)) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    // GETTERY
    public static String getUsername() {
        return username;
    }

    public static String getTheme() {
        return theme;
    }

    // SETTERY
    public static void setUsername(String username) {
        AppSettings.username = username;
    }

    public static void setTheme(String theme) {
        AppSettings.theme = theme;
    }
}