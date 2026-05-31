package Game;

import Game.GameObjects.*;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Helper class that reads level layouts from JSON configuration files.
 * It parses text grids and translates characters into actual game objects.
 *
 * @author Filip Honomichl
 */
public class LevelReader {

    private static class JsonStructure {
        public ArrayList<String> level1;
    }

    /**
     * Connects to the specified JSON file, reads its raw content, 
     * and uses Gson to map it into a list of text rows representing the layout.
     */
    public static ArrayList<String> loadLevelLines(String resourcePath) {
        Gson gson = new Gson();
        try (InputStream is = LevelReader.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new IllegalStateException("File not found: " + resourcePath);

            JsonStructure data = gson.fromJson(
                    new InputStreamReader(is, StandardCharsets.UTF_8),
                    JsonStructure.class
            );
            return data.level1;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Loops through the rows and characters of a loaded level file. It converts letters 
     * like 'P', 'b', 's', or '=' into objects at grid coordinates, and loads them into the game canvas.
     */
    public static boolean loadLevel(String path, GamePanel panel) {
        ArrayList<String> radky = loadLevelLines(path);

        /** temporary lists */
        Player tempPlayer = null;
        ArrayList<Block> tempBlocks = new ArrayList<>();
        ArrayList<Spike> tempSpikes = new ArrayList<>();
        ArrayList<Floor> tempFloors = new ArrayList<>();
        End tempEnd = null;

        if (radky == null) {
            return false;
        }

        for (int r = 0; r < radky.size(); r++) {
            String radek = radky.get(r);

            for (int c = 0; c < radek.length(); c++) {
                char znak = radek.charAt(c);
                int x = c * 40;
                int y = r * 40;

                if (znak == 'P') {
                    tempPlayer = new Player(x, y);
                } else if (znak == 'b') {
                    tempBlocks.add(new Block(x, y));
                } else if (znak == 's') {
                    tempSpikes.add(new Spike(x, y));
                } else if (znak == '=' ) {
                    tempFloors.add(new Floor(x, y));
                } else if (znak == 'e') {
                    tempEnd = new End(x);
                }
            }
        }

        if (tempPlayer == null || tempBlocks.isEmpty() && tempSpikes.isEmpty()) {
            return false;
        }

        /** saves everything to GamePanel */
        panel.setPlayer(tempPlayer);
        panel.setBlocks(tempBlocks);
        panel.setSpikes(tempSpikes);
        panel.setFloors(tempFloors);
        panel.setEnd(tempEnd);
        return true;
    }
}