
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LevelReader {
    private static class JsonStructure {
        public ArrayList<String> level1;
    }

    // Metoda už nemá žádné složité parametry, prostě jen vrátí načtené řádky textu
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
}
