package io.tidalisland.tide;

import io.tidalisland.config.Config;
import io.tidalisland.entities.Player;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.TileSet;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the tidal mechanic that gradually floods the island.
 */
public class TidalManager {
  private final WorldMap worldMap;
  private final WorldObjectManager worldObjectManager;
  private final TileSet tileSet;
  private final Player player;
  private long lastFloodTime;
  private final long minFloodInterval;
  private final long maxFloodInterval;
  private long currentFloodInterval;
  private int waterTileId = -1;

  private boolean fullyFlooded = false;

  /**
   * Creates a new tidal manager.
   */
  public TidalManager(
      WorldMap worldMap, WorldObjectManager worldObjectManager, TileSet tileSet, Player player,
      double minFloodIntervalSeconds, double maxFloodIntervalSeconds) {
    if (minFloodIntervalSeconds <= 0 || maxFloodIntervalSeconds < minFloodIntervalSeconds) {
      throw new IllegalArgumentException("Invalid flood interval range");
    }

    this.worldMap = worldMap;
    this.worldObjectManager = worldObjectManager;
    this.tileSet = tileSet;
    this.player = player;
    this.minFloodInterval = (long) (minFloodIntervalSeconds * 1000);
    this.maxFloodInterval = (long) (maxFloodIntervalSeconds * 1000);
    this.currentFloodInterval = randomInterval();
    this.lastFloodTime = System.currentTimeMillis();

    // Find water tile ID
    this.waterTileId = this.tileSet.get("water");

    if (waterTileId == -1) {
      throw new IllegalArgumentException("Water tile not found in tileset!");
    }
  }

  /**
   * Updates the tidal system.
   */
  public void update() {
    if (fullyFlooded) {
      return;
    }

    long now = System.currentTimeMillis();
    if (now - lastFloodTime >= currentFloodInterval) {
      floodNextWave();
      lastFloodTime = now;
      currentFloodInterval = randomInterval();
    }
  }

  /**
   * Floods the next wave of tiles adjacent to water.
   */
  private void floodNextWave() {
    Set<Position> tilesToFlood = findTilesToFlood();

    if (tilesToFlood.isEmpty()) {
      fullyFlooded = true;
      return;
    }

    // Flood the tiles
    for (Position pos : tilesToFlood) {
      floodTile(pos);

    }
  }

  /**
   * Floods a single tile.
   */
  private void floodTile(Position pos) {
    int col = pos.getX();
    int row = pos.getY();

    // Turn tile into water
    worldMap.setTile(col, row, waterTileId);

    // Destroy world objects on this tile
    Position worldPos = new Position(col * Config.tileSize(), row * Config.tileSize());
    WorldObject obj = worldObjectManager.get(worldPos);
    if (obj != null) {
      worldObjectManager.remove(obj);
    }

    // Push player if they're on this tile
    pushPlayerAway(col, row);
  }

  /**
   * Finds all tiles that should be flooded in the next wave.
   */
  private Set<Position> findTilesToFlood() {
    Set<Position> candidates = new HashSet<>();

    for (int row = 0; row < Config.mapHeight(); row++) {
      for (int col = 0; col < Config.mapWidth(); col++) {
        Tile tile = worldMap.getTile(col, row);

        // Skip if already water
        if (tile != null && tile.getId() == waterTileId) {
          continue;
        }

        // Check if adjacent to water
        if (isAdjacentToWater(col, row)) {
          candidates.add(new Position(col, row));
        }
      }
    }

    return candidates;
  }

  /**
   * Checks if a tile is adjacent to water (including edges).
   */
  private boolean isAdjacentToWater(int col, int row) {
    // Check all 4 cardinal directions
    int[][] directions = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };

    for (int[] dir : directions) {
      int neighborCol = col + dir[0];
      int neighborRow = row + dir[1];

      // Treat edges as water
      if (neighborCol < 0 || neighborCol >= Config.mapWidth() || neighborRow < 0
          || neighborRow >= Config.mapHeight()) {
        return true;
      }

      // Check if neighbor is water
      Tile neighborTile = worldMap.getTile(neighborCol, neighborRow);
      if (neighborTile != null && neighborTile.getId() == waterTileId) {
        return true;
      }
    }

    return false;
  }

  /**
   * Pushes the player away if they're standing on a tile that's being flooded.
   */
  private void pushPlayerAway(int floodCol, int floodRow) {
    Position playerPos = player.getPosition();
    int playerCol = playerPos.getX() / Config.tileSize();
    int playerRow = playerPos.getY() / Config.tileSize();

    if (playerCol != floodCol || playerRow != floodRow) {
      return;
    }

    Position safeTile = findNearestSafeTile(floodCol, floodRow);
    if (safeTile != null) {
      playerPos.setPosition(safeTile);
      player.getCollider().updatePosition(safeTile);
    } else {
      fullyFlooded = true;
    }
  }

  /**
   * Finds the nearest safe tile.
   */
  private Position findNearestSafeTile(int startCol, int startRow) {
    int width = Config.mapWidth();
    int height = Config.mapHeight();

    boolean[][] visited = new boolean[height][width];
    ArrayDeque<int[]> queue = new ArrayDeque<>();

    queue.add(new int[] { startCol, startRow });
    visited[startRow][startCol] = true;

    int[][] dirs = {
        { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }
    };

    while (!queue.isEmpty()) {
      int[] cur = queue.poll();
      int col = cur[0];
      int row = cur[1];

      Tile tile = worldMap.getTile(col, row);
      if (tile != null && isSafeTile(col, row)) {
        return new Position(
            col * Config.tileSize(),
            row * Config.tileSize());
      }

      for (int[] d : dirs) {
        int nc = col + d[0];
        int nr = row + d[1];

        if (nc < 0 || nc >= width || nr < 0 || nr >= height) {
          continue;
        }
        if (visited[nr][nc]) {
          continue;
        }

        visited[nr][nc] = true;
        queue.add(new int[] { nc, nr });
      }
    }

    return null;
  }

  /**
   * Checks if a tile is safe.
   */
  private boolean isSafeTile(int col, int row) {
    Tile tile = worldMap.getTile(col, row);
    if (tile == null) {
      return false;
    }
    if (tile.getId() == waterTileId) {
      return false;
    }
    if (tile.isSolid()) {
      return false;
    }

    Position worldPos = new Position(
        col * Config.tileSize(),
        row * Config.tileSize());

    return !worldObjectManager.has(worldPos);
  }

  /**
   * Returns the time remaining until the next flood (in seconds).
   */
  public double getTimeUntilNextFlood() {
    long currentTime = System.currentTimeMillis();
    long timeSinceLastFlood = currentTime - lastFloodTime;
    long timeRemaining = currentFloodInterval - timeSinceLastFlood;
    return Math.max(0, timeRemaining / 1000.0);
  }

  /**
   * Returns the percentage of time until the next flood (0.0 to 1.0).
   */
  public double getFloodProgress() {
    long currentTime = System.currentTimeMillis();
    long timeSinceLastFlood = currentTime - lastFloodTime;
    return Math.min(1.0, (double) timeSinceLastFlood / currentFloodInterval);
  }

  /**
   * Returns true if the island is fully flooded.
   */
  public boolean isFullyFlooded() {
    return fullyFlooded;
  }

  /**
   * Resets the tidal manager.
   */
  public void reset() {
    fullyFlooded = false;
    lastFloodTime = System.currentTimeMillis();
  }

  private long randomInterval() {
    return ThreadLocalRandom.current()
        .nextLong(minFloodInterval, maxFloodInterval + 1);
  }
}
