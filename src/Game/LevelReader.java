
package Game;

import Game.GameObjects.*;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LevelReader {

    private static class JsonStructure {
        public ArrayList<String> level1;
    }

    /**
     * loads data from json file using gson
     * returns String Arraylist of obstacles
     */
    public static ArrayList<String> loadLevelLines(String resourcePath) {
        Gson gson = new Gson();
        try (InputStream is = LevelReader.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new IllegalStateException("Soubor nenalezen: " + resourcePath);

            JsonStructure data = gson.fromJson(
                    new InputStreamReader(is, StandardCharsets.UTF_8),
                    JsonStructure.class
            );
            return data.level1;
        } catch (Exception e) {
            throw new RuntimeException("Chyba při načítání: " + e.getMessage());
        }
    }

    /**
     * Processes level data from {@link #loadLevelLines(String)}.
     *
     * Scans the lines character by character to identify and map objects
     * ('P' for player, 'b' for blocks, 's' for spikes, '=' for floors)
     * into temporary lists, then saves them to GamePanel.
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
