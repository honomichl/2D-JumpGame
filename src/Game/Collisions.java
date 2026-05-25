package Game;

import Game.GameObjects.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Obsahuje čistě statické metody pro výpočet a řešení herních kolizí.
 */
public class Collisions {

    /**
     * Kontroluje smrtelné kolize (náraz do spiku nebo čelní náraz do bloku).
     * @return true, pokud hráč zemřel a level se musí resetovat.
     */
    public static boolean checkDeathCollisions(Player player, ArrayList<Spike> spikes, ArrayList<Block> blocks, int cameraX) {

        // Vytvoříme hitbox hráče v globálních souřadnicích celého světa
        Rectangle playerWorldBox = new Rectangle(
                player.getX() + cameraX,
                player.getY(),
                player.getSize(),
                player.getSize()
        );

        // 1. Kolize se spiky (jakýkoliv dotek se spikem znamená smrt)
        for (Spike spike : spikes) {
            if (spike.getHitbox().intersects(playerWorldBox)) {
                return true;
            }
        }

        // 2. Čelní náraz do bloku (ze strany)
        for (Block block : blocks) {
            Rectangle blockBox = block.getHitbox();

            if (playerWorldBox.intersects(blockBox)) {
                // Pokud spodní hrana hráče není dostatečně vysoko nad blokem,
                // bereme to jako čelní náraz (stěna) a hráč umírá.
                boolean landingFromAbove = (player.getY() + player.getSize()) - player.getJumpSpeed() <= block.getY() + 10;

                if (!landingFromAbove && player.getJumpSpeed() >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Řeší bezpečné přistání hráče na blocích a na tvé nové podlaze/stropu.
     */
    public static void handleLanding(Player player, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {

        Rectangle playerWorldBox = new Rectangle(
                player.getX() + cameraX,
                player.getY(),
                player.getSize(),
                player.getSize()
        );

        // Na začátku předpokládáme, že hráč padá (pokud ho něco nepodepře)
        player.setOnGround(false);

        // 1. Přistání na blocích (b)
        for (Block block : blocks) {
            Rectangle blockBox = block.getHitbox();

            if (playerWorldBox.intersects(blockBox)) {
                // Kontrola, zda hráč padá shora dolů na horní hranu bloku
                if ((player.getY() + player.getSize()) - player.getJumpSpeed() <= block.getY() + 10 && player.getJumpSpeed() >= 0) {
                    player.setY(block.getY() - player.getSize()); // Zarovnáme hráče přesně na blok
                    player.setJumpSpeed(0);
                    player.setOnGround(true);
                    return; // Pokud přistál, nemusíme kontrolovat další objekty
                }
            }
        }

        // 2. Přistání na tvé nové podlaze nebo stropu (=)
        for (Floor floor : floors) {
            Rectangle floorBox = floor.getHitbox();

            if (playerWorldBox.intersects(floorBox)) {

                // A) PŘISTÁNÍ NA PODLAZE (Hráč padá shora dolů)
                if ((player.getY() + player.getSize()) - player.getJumpSpeed() <= floor.getY() + 10 && player.getJumpSpeed() >= 0) {
                    player.setY(floor.getY() - player.getSize()); // Zarovnání na podlahu
                    player.setJumpSpeed(0);
                    player.setOnGround(true);
                    return;
                }

                // B) NÁRAZ DO STROPU (Hráč skáče nahoru a narazí hlavou do row003)
                else if (player.getJumpSpeed() < 0 && player.getY() >= floor.getY() + floorBox.height - 10) {
                    player.setY(floor.getY() + floorBox.height); // Odrazíme ho pod strop
                    player.setJumpSpeed(0); // Zastavíme stoupání, začne padat
                }
            }
        }
    }
}