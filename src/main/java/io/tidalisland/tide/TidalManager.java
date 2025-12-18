package io.tidalisland.tide;

import io.tidalisland.collision.Collider;
import io.tidalisland.collision.CollisionManager;
import io.tidalisland.config.Config;
import io.tidalisland.engine.GameClock;
import io.tidalisland.entities.Player;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.TileSet;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.Raft;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectManager;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the tidal mechanic that floods the island.
 */
public class TidalManager {

  private final WorldMap worldMap;
  private final WorldObjectManager worldObjectManager;
  private final CollisionManager collisionManager;
  private final TileSet tileSet;
  private final Player player;

  private final long minFloodInterval;
  private final long maxFloodInterval;

  private int waterTileId = -1;
  private long currentFloodInterval;
  private long elapsedSinceLastFlood = 0;

  private boolean fullyFlooded = false;

  /**
   * Creates a new tidal manager.
   */
  public TidalManager(
      WorldMap worldMap, WorldObjectManager worldObjectManager, CollisionManager collisionManager,
      TileSet tileSet, Player player,
      double minFloodIntervalSeconds, double maxFloodIntervalSeconds) {
    if (minFloodIntervalSeconds <= 0 || maxFloodIntervalSeconds < minFloodIntervalSeconds) {
      throw new IllegalArgumentException("Invalid flood interval range");
    }

    this.worldMap = worldMap;
    this.worldObjectManager = worldObjectManager;
    this.collisionManager = collisionManager;
    this.tileSet = tileSet;
    this.player = player;
    this.minFloodInterval = (long) (minFloodIntervalSeconds * 1000);
    this.maxFloodInterval = (long) (maxFloodIntervalSeconds * 1000);
    this.currentFloodInterval = randomInterval();

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

    long deltaMillis = GameClock.getInstance().getDeltaMillis();
    elapsedSinceLastFlood += deltaMillis;

    if (elapsedSinceLastFlood >= currentFloodInterval) {
      floodNextWave();
      elapsedSinceLastFlood = 0;
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
    if (obj != null && !obj.isFloatable()) {
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
    if (!collisionManager.isOnTile(player.getCollider(), floodRow, floodCol)) {
      return; // player is not on the flooded tile, no pushback
    }
    if (collisionManager.isOnObject(player.getCollider(), Raft.TYPE)) {
      return; // player is safe on raft, no pushback
    }

    Position safeTile = findNearestSafeTile(floodCol, floodRow);
    if (safeTile != null) {
      player.getPosition().setPosition(safeTile);
      player.getCollider().updatePosition(safeTile);
    } else {
      fullyFlooded = true;
    }
  }

  /**
   * Finds the nearest safe tile using a breadth-first search.
   */
  private Position findNearestSafeTile(int startCol, int startRow) {
    int mapWidth = Config.mapWidth();
    int mapHeight = Config.mapHeight();

    // Create a boolean array to track visited tiles
    boolean[][] visited = new boolean[mapHeight][mapWidth];
    // Create a queue to store the tiles to visit
    ArrayDeque<int[]> queue = new ArrayDeque<>();

    // Visit the starting tile
    queue.add(new int[] { startCol, startRow });
    visited[startRow][startCol] = true;

    // Create a list of cardinal directions to traverse adjacent tiles
    int[][] dirs = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };

    while (!queue.isEmpty()) {
      // Pop the next tile from the queue
      int[] cur = queue.poll();
      int col = cur[0];
      int row = cur[1];
      Tile tile = worldMap.getTile(col, row);

      // Check if the tile is safe
      if (tile != null && isSafeTile(col, row)) {
        // Return the tile position
        return new Position(col * Config.tileSize(), row * Config.tileSize());
      }

      // Traverse adjacent tiles
      for (int[] d : dirs) {
        int nc = col + d[0];
        int nr = row + d[1];

        // Check if the adjacent tile is within the map bounds
        if (nc < 0 || nc >= mapWidth || nr < 0 || nr >= mapHeight) {
          continue;
        }
        // Check if the adjacent tile has already been visited
        if (visited[nr][nc]) {
          continue;
        }

        // Visit the adjacent tile
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

    // Tile not found, not safe
    if (tile == null) {
      return false;
    }

    // Check for any object overlapping this tile
    for (WorldObject obj : worldObjectManager.getAll()) {
      if (collisionManager.isOnTile(obj.getCollider(), row, col)) {
        // There is a floatable object overlapping this tile, safe
        if (obj.isFloatable()) {
          return true;
        }
        // There is an object overlapping this tile, not safe
        return false;
      }
    }

    // Water without floatable is unsafe
    if (tile.getId() == waterTileId) {
      return false;
    }

    // Solid terrain blocks player
    if (tile.isSolid()) {
      return false;
    }

    return true;
  }

  /**
   * Returns the time remaining until the next flood (in seconds).
   */
  public double getTimeUntilNextFlood() {
    long remaining = currentFloodInterval - elapsedSinceLastFlood;
    return Math.max(0, remaining / 1000.0);
  }

  /**
   * Returns the percentage of time until the next flood (0.0 to 1.0).
   */
  public double getFloodProgress() {
    return Math.min(1.0, (double) elapsedSinceLastFlood / currentFloodInterval);
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
    elapsedSinceLastFlood = 0;
  }

  private long randomInterval() {
    return ThreadLocalRandom.current().nextLong(minFloodInterval, maxFloodInterval + 1);
  }
}
