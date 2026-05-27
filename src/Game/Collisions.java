package Game;

import Game.GameObjects.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Obsahuje čistě statické metody pro výpočet a řešení herních kolizí.
 */
public class Collisions {
    // Konstanty se v Javě píšou velkými písmeny
    private static final int TOLERANCE = 10;

    /**
     * Kontroluje smrtelné kolize (náraz do spiku nebo čelní náraz do bloku/podlahy).
     *
     * @return true, pokud hráč zemřel a level se musí resetovat.
     */
    public static boolean checkDeathCollision(Player player, ArrayList<Spike> spikes, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {
        Rectangle deathHitbox = player.getSmallHitbox();
        Rectangle playerWorldBox = new Rectangle(deathHitbox.x + cameraX, deathHitbox.y, deathHitbox.width, deathHitbox.height);

        // Kolize se spiky
        for (Spike spike : spikes) {
            if (spike.getHitbox().intersects(playerWorldBox)) {
                return true;
            }
        }

        // Smrtící kolize s bloky
        for (Block block : blocks) {
            if (isDeadlyCollision(player, playerWorldBox, block.getHitbox())) {
                return true;
            }
        }

        // Smrtící kolize s podlahou
        for (Floor floor : floors) {
            if (isDeadlyCollision(player, playerWorldBox, floor.getHitbox())) {
                return true;
            }
        }

        return false;
    }

    /**
     * ČISTĚ PŘISTÁNÍ ZDE: Řeší pouze bezpečné položení hráče na horní hranu bloků a podlah.
     */
    public static void handleLanding(Player player, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {
        Rectangle landingHitbox = player.getBigHitbox();
        Rectangle playerWorldBox = new Rectangle(landingHitbox.x + cameraX, landingHitbox.y, landingHitbox.width, landingHitbox.height);

        player.setOnGround(false);

        // Přistání na blocích
        for (Block block : blocks) {
            if (tryLandOnObstacle(player, playerWorldBox, block.getHitbox())) return;
        }

        // Přistání na podlaze
        for (Floor floor : floors) {
            if (tryLandOnObstacle(player, playerWorldBox, floor.getHitbox())) return;
        }
    }

    /** * Kontroluje, zda hráč dorazil do konce levelu.
     */
    public static boolean theEnd(Player player, End end, int cameraX) {
        if (end == null) return false;

        // Pro konec levelu je férovější použít velký hitbox
        Rectangle hitbox = player.getBigHitbox();
        Rectangle playerWorldBox = new Rectangle(hitbox.x + cameraX, hitbox.y, hitbox.width, hitbox.height);

        return playerWorldBox.intersects(end.getHitbox());
    }

    // ==========================================
    // SOUKROMÉ POMOCNÉ METODY PRO ZABRÁNĚNÍ DUPLICITĚ
    // ==========================================

    private static boolean isDeadlyCollision(Player player, Rectangle playerWorldBox, Rectangle obstacleBox) {
        if (!playerWorldBox.intersects(obstacleBox)) return false;

        // Kolize seshora (přistání)
        boolean landingFromAbove = (player.getY() + player.getSize()) - player.getJumpSpeed() <= obstacleBox.y + TOLERANCE;

        // Smrt zespodu (náraz hlavou) nebo z boku
        boolean hitFromBelow = player.getJumpSpeed() < 0 && player.getY() - player.getJumpSpeed() >= obstacleBox.y + obstacleBox.height - TOLERANCE;

        return !landingFromAbove || hitFromBelow;
    }

    private static boolean tryLandOnObstacle(Player player, Rectangle playerWorldBox, Rectangle obstacleBox) {
        if (!playerWorldBox.intersects(obstacleBox)) return false;

        boolean landingFromAbove = (player.getY() + player.getSize()) - player.getJumpSpeed() <= obstacleBox.y + TOLERANCE;

        // Pokud padá dolů a je nad překážkou, přistane
        if (landingFromAbove && player.getJumpSpeed() >= 0) {
            player.setY(obstacleBox.y - player.getSize());
            player.setJumpSpeed(0);
            player.setOnGround(true);
            return true; // Podařilo se přistát
        }

        return false;
    }
}