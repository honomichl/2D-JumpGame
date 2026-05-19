
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//TODO upravit nazvy a celkove

public class LevelReader {

    // Pomocná vnitřní struktura, která přesně odpovídá struktuře našeho JSONu
    private static class JsonStructure {
        public ArrayList<String> level1;
    }

    /**
     * Načte JSON z resources a podle znaků vytvoří seznamy herních objektů.
     */
    public static void loadLevel(String resourcePath, ArrayList<Block> blocks, ArrayList<Spike> spikes, Level levelInstance) {
        Gson gson = new Gson();

        try (InputStream is = LevelReader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalStateException("Nenalezen soubor s levelem: " + resourcePath);
            }

            // 1. Gson načte text z JSONu do naší pomocné struktury
            JsonStructure data = gson.fromJson(
                    new InputStreamReader(is, StandardCharsets.UTF_8),
                    JsonStructure.class
            );

            if (data.level1 == null) return;

            // 2. Projdeme řádky z JSONu (shora dolů)
            for (int r = 0; r < data.level1.size(); r++) {
                String radek = data.level1.get(r);

                // 3. Projdeme znaky v řádku (zleva doprava)
                for (int c = 0; c < radek.length(); c++) {
                    char znak = radek.charAt(c);

                    // Výpočet souřadnic na mřížce (velikost 40 px)
                    int x = c * 40;
                    int y = r * 40;

                    // 4. Podle znaku vytvoříme správný objekt
                    if (znak == 'P') {
                        // Nastavíme startovní výšku hráče přímo do instance Levelu
                        levelInstance.setPlayerY(y);
                    } else if (znak == 'b') {
                        blocks.add(new Block(x, y));
                    } else if (znak == 's') {
                        // Tady předpokládám, že tvůj Spike už umí přijmout souřadnici Y v konstruktoru
                        spikes.add(new Spike(x, y));
                    } else if (znak == '=') {
                        // Zde si v budoucnu můžeš odchytit čáry pro speciální podlahy/stropy
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Chyba při parsování JSON levelu: " + e.getMessage());
        }
    }
}