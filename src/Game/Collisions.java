package Game;

import Game.GameObjects.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Utility class filled with static methods to calculate and handle all in-game
 * collision logic, such as landing on platforms, hitting spikes, or reaching the finish line.
 *
 * @author Filip Honomichl
 */
public class Collisions {
    private static final int tolerance = 15;

    /**
     * Checks if the player collided with something deadly, like a spike or
     * running head-first into a wall, which requires a level reset.
     */
    public static boolean checkDeathCollision(Player player, ArrayList<Spike> spikes, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {
        Rectangle deathHitbox = player.getSmallHitbox();

        /** player hitbox based on location */
        Rectangle playerWorldBox = new Rectangle(deathHitbox.x + cameraX, deathHitbox.y, deathHitbox.width, deathHitbox.height);

        /** collision with spikes */
        for (Spike spike : spikes) {
            if (spike.getHitbox().intersects(playerWorldBox)) {
                return true;
            }
        }

        /** deadly collision with box */
        for (Block block : blocks) {
            if (playerWorldBox.intersects(block.getHitbox())) {
                if (!contactFromAbove(player, block.getHitbox()) || contactFromBelow(player, block.getHitbox())) {
                    return true;
                }
            }
        }

        /** deadly collisio with roof */
        for (Floor floor : floors) {
            if (playerWorldBox.intersects(floor.getHitbox())) {
                if (!contactFromAbove(player, floor.getHitbox()) || contactFromBelow(player, floor.getHitbox())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Handles safe landings by snapping the player coordinates to the top edge
     * of a block or floor structure when falling onto them.
     */
    public static void handleLanding(Player player, ArrayList<Block> blocks, ArrayList<Floor> floors, int cameraX) {
        Rectangle landingHitbox = player.getBigHitbox();

        /** player hitbox based on location */
        Rectangle playerWorldBox = new Rectangle(landingHitbox.x + cameraX, landingHitbox.y, landingHitbox.width, landingHitbox.height);
        player.setOnGround(false);

        /** landing on blocks */
        for (Block block : blocks) {
            if (playerWorldBox.intersects(block.getHitbox())) {
                /** collision from above */
                if (contactFromAbove(player, block.getHitbox()) && player.getJumpSpeed() >= 0) {
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
                if (contactFromAbove(player, floor.getHitbox()) && player.getJumpSpeed() >= 0) {
                    player.setY(floor.getY() - player.getSize());
                    player.setJumpSpeed(0);
                    player.setOnGround(true);
                    return;
                }
            }
        }
    }

    /**
     * checks if player reached the end
     */
    public static boolean theEnd(Player player, End end, int cameraX) {
        if (end == null) {
            return false;
        }

        Rectangle playerWorldBox = new Rectangle(player.getX() + cameraX, player.getY(), player.getSize(), player.getSize());

        return playerWorldBox.intersects(end.getHitbox());
    }

    /**
     * Helper method checking if the player's downward trajectory originates
     * from a position above the specified obstacle hitbox.
     */
    public static boolean contactFromAbove(Player player, Rectangle obstacleHitbox) {
        return (player.getY() + player.getSize()) - player.getJumpSpeed() <= obstacleHitbox.getY() + tolerance;
    }

    /**
     * Helper method checking if the player is moving upward and hits the bottom
     * of an obstacle.
     */
    public static boolean contactFromBelow(Player player, Rectangle obstacleHitbox) {
        return player.getJumpSpeed() < 0 && player.getY() - player.getJumpSpeed() >= obstacleHitbox.y + obstacleHitbox.height - tolerance;
    }
}