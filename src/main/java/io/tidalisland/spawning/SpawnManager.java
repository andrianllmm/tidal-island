package io.tidalisland.spawning;

import io.tidalisland.config.Config;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages spawn positions for entities in the world.
 */
public class SpawnManager {

  private final WorldMap worldMap;
  private final WorldObjectManager worldObjectManager;
  private final Random random;

  /**
   * Creates a new spawn manager.
   */
  public SpawnManager(WorldMap worldMap, WorldObjectManager worldObjectManager) {
    this.worldMap = worldMap;
    this.worldObjectManager = worldObjectManager;
    this.random = Config.random();
  }

  /**
   * Finds a random valid spawn position anywhere in the world. A valid position is one where the
   * tile is not solid and has no world object.
   */
  public Position findValidSpawnPosition() {
    List<Position> validPositions = getAllValidPositions();

    // If no valid positions found, return center
    if (validPositions.isEmpty()) {
      System.err.println("Warning: No valid spawn positions found! Using center position.");
      return getCenterPosition();
    }

    // Pick a random valid position
    Position chosenPosition = validPositions.get(random.nextInt(validPositions.size()));

    return chosenPosition;
  }

  /**
   * Finds a valid spawn position near a target position within a given radius. Searches in a square
   * area around the target position.
   */
  public Position findSpawnNearPosition(Position target, int radius) {
    if (target == null || radius < 0) {
      return null;
    }

    List<Position> validPositions = new ArrayList<>();

    int targetCol = target.getX() / Config.tileSize();
    int targetRow = target.getY() / Config.tileSize();

    // Search in a square area around the target
    for (int row = targetRow - radius; row <= targetRow + radius; row++) {
      for (int col = targetCol - radius; col <= targetCol + radius; col++) {
        // Skip out of bounds
        if (col < 0 || col >= Config.mapWidth() || row < 0 || row >= Config.mapHeight()) {
          continue;
        }

        Position pos = new Position(col * Config.tileSize(), row * Config.tileSize());

        if (isValidSpawnPosition(pos)) {
          validPositions.add(pos);
        }
      }
    }

    // If no valid positions found nearby, return null
    if (validPositions.isEmpty()) {
      return null;
    }

    // Pick closest valid position (or random if you prefer)
    return getClosestPosition(validPositions, target);
  }

  /**
   * Finds multiple valid spawn positions. Useful for spawning groups of entities or items.
   */
  public List<Position> findMultipleSpawns(int count) {
    if (count <= 0) {
      return new ArrayList<>();
    }

    List<Position> allValid = getAllValidPositions();
    List<Position> selected = new ArrayList<>();

    // Shuffle and take up to count positions
    List<Position> shuffled = new ArrayList<>(allValid);
    for (int i = shuffled.size() - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);
      Position temp = shuffled.get(i);
      shuffled.set(i, shuffled.get(j));
      shuffled.set(j, temp);
    }

    for (int i = 0; i < Math.min(count, shuffled.size()); i++) {
      selected.add(shuffled.get(i));
    }

    return selected;
  }

  /**
   * Checks if a specific position is valid for spawning.
   */
  public boolean isValidSpawnPosition(Position position) {
    if (position == null) {
      return false;
    }

    int col = position.getX() / Config.tileSize();
    int row = position.getY() / Config.tileSize();

    // Check bounds
    if (col < 0 || col >= Config.mapWidth() || row < 0 || row >= Config.mapHeight()) {
      return false;
    }

    Tile tile = worldMap.getTile(col, row);

    // Check if tile exists, is not solid, and has no world object
    return tile != null && !tile.isSolid() && !worldObjectManager.has(position);
  }

  /**
   * Gets all valid spawn positions in the world.
   */
  private List<Position> getAllValidPositions() {
    List<Position> validPositions = new ArrayList<>();

    for (int row = 0; row < Config.mapHeight(); row++) {
      for (int col = 0; col < Config.mapWidth(); col++) {
        Position pos = new Position(col * Config.tileSize(), row * Config.tileSize());

        if (isValidSpawnPosition(pos)) {
          validPositions.add(pos);
        }
      }
    }

    return validPositions;
  }

  /**
   * Gets the center position of the map.
   */
  private Position getCenterPosition() {
    int centerCol = Config.mapWidth() / 2;
    int centerRow = Config.mapHeight() / 2;
    return new Position(centerCol * Config.tileSize(), centerRow * Config.tileSize());
  }

  /**
   * Finds the closest position to a target from a list of positions.
   */
  private Position getClosestPosition(List<Position> positions, Position target) {
    if (positions.isEmpty()) {
      return null;
    }

    Position closest = positions.get(0);
    int minDistance = target.distanceTo(closest);

    for (Position pos : positions) {
      int distance = target.distanceTo(pos);
      if (distance < minDistance) {
        minDistance = distance;
        closest = pos;
      }
    }

    return closest;
  }
}
