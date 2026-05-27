package Game;

import Game.GameObjects.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Obsahuje čistě statické metody pro výpočet a řešení herních kolizí.
 */
public class Collisions {
    private static final int tolerance = 10;

    /**
     * Kontroluje smrtelné kolize (náraz do spiku nebo čelní náraz do bloku).
     *
     * @return true, pokud hráč zemřel a level se musí resetovat.
     */
    public static boolean checkDeathCollision(Player player, ArrayList<Spike> spikes, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {

        /** player hitbox based on location */
        Rectangle playerWorldBox = new Rectangle(player.getX() + cameraX, player.getY(), player.getSize(), player.getSize());

        /** collision with spikes */
        for (Spike spike : spikes) {
            if (spike.getHitbox().intersects(playerWorldBox)) {
                return true;
            }
        }

        /** deadly collision with box */
        for (Block block : blocks) {
            Rectangle blockBox = block.getHitbox();

            if (playerWorldBox.intersects(blockBox)) {
                /** collision from above */
                boolean landingFromAbove = (player.getY() + player.getSize()) - player.getJumpSpeed() <= block.getY() + tolerance;
                /** death from below */
                boolean hitFromBelow = player.getJumpSpeed() < 0 && player.getY() - player.getJumpSpeed() >= block.getY() + blockBox.height - tolerance;

                if (!landingFromAbove || hitFromBelow) {
                    return true;
                }
            }
        }

        /** deadly collisio with roof */
        for (Floor floor : floors) {
            Rectangle floorBox = floor.getHitbox();

            if (playerWorldBox.intersects(floorBox)) {
                /** collision from above */
                boolean landingFromAbove = (player.getY() + player.getSize()) - player.getJumpSpeed() <= floor.getY() + tolerance;

                /** collision from below */
                boolean hitFromBelow = player.getJumpSpeed() < 0 && player.getY() - player.getJumpSpeed() >= floor.getY() + floorBox.height - tolerance;

                if (!landingFromAbove || hitFromBelow) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ČISTĚ PŘISTÁNÍ ZDE: Řeší pouze bezpečné položení hráče na horní hranu bloků a podlah.
     * Tato metoda nikoho nezabíjí, pouze zarovnává souřadnice.
     */
    public static void handleLanding(Player player, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {

        /** player hitbox based on location */
        Rectangle playerWorldBox = new Rectangle(player.getX() + cameraX, player.getY(), player.getSize(), player.getSize());

        player.setOnGround(false);

        /** landing on blocks */
        for (Block block : blocks) {
            if (playerWorldBox.intersects(block.getHitbox())) {
                /** collision from above */
                if ((player.getY() + player.getSize()) - player.getJumpSpeed() <= block.getY() + tolerance && player.getJumpSpeed() >= 0) {
                    player.setY(block.getY() - player.getSize());
                    player.setJumpSpeed(0);
                    player.setOnGround(true);
                    return;
                }
            }
        }

        /** landing on floor */
        for (Floor floor : floors) {
            if (playerWorldBox.intersects(floor.getHitbox())) {
                /** collision from above */
                if ((player.getY() + player.getSize()) - player.getJumpSpeed() <= floor.getY() + tolerance && player.getJumpSpeed() >= 0) {
                    player.setY(floor.getY() - player.getSize());
                    player.setJumpSpeed(0);
                    player.setOnGround(true);
                    return;
                }
            }
        }
    }
}