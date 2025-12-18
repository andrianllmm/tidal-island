package io.tidalisland.collision;

import io.tidalisland.config.Config;
import io.tidalisland.entities.Entity;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectManager;
import io.tidalisland.worldobjects.WorldObjectType;

/**
 * Manages collisions between entities and the world.
 */
public class CollisionManager {

  private final WorldMap worldMap;
  private final WorldObjectManager worldObjectManager;
  private int collisionCount;

  /**
   * Creates a new collision manager.
   *
   * @param worldMap the world map
   * @param worldObjectManager the world object manager
   */
  public CollisionManager(WorldMap worldMap, WorldObjectManager worldObjectManager) {
    this.worldMap = worldMap;
    this.worldObjectManager = worldObjectManager;
  }

  /**
   * Checks if an entity can move to a new position without colliding.
   *
   * @param entity the entity
   * @param nextX the next x-coordinate
   * @param nextY the next y-coordinate
   * @return true if the entity can move to the new position, false otherwise
   */
  public boolean canMove(Entity entity, int nextX, int nextY) {
    // Create a hypothetical collider at the new position
    Collider future = entity.getCollider().copy();
    future.updatePosition(nextX, nextY);

    // Check collision with world tiles
    if (getCollidingTile(future) != null) {
      collisionCount++;
      return false;
    }

    // Check collision with world objects
    WorldObject worldObj = getCollidingObject(future);
    if (worldObj != null && worldObj.isSolid()) {
      collisionCount++;
      return false;
    }

    return true;
  }

  /**
   * Checks if an entity can move to a new position without colliding.
   *
   * @param entity the entity
   * @param nextPosition the next position
   * @return true if the entity can move to the new position, false otherwise
   */
  public boolean canMove(Entity entity, Position nextPosition) {
    return canMove(entity, nextPosition.getX(), nextPosition.getY());
  }

  /**
   * Checks if a collider intersects any solid tiles in the world.
   *
   * @param collider the collider
   * @return true if the collider intersects a solid tile, false otherwise
   */
  public Tile getCollidingTile(Collider collider) {
    // Check if the player is standing on any floatable object
    for (WorldObject obj : worldObjectManager.getAll()) {
      if (obj.isFloatable() && obj.getCollider().contains(collider)) {
        return null;
      }
    }

    // Check collisions with solid tiles
    int startCol = collider.getX() / Config.tileSize();
    int startRow = collider.getY() / Config.tileSize();
    int endCol = (collider.getX() + collider.getWidth()) / Config.tileSize();
    int endRow = (collider.getY() + collider.getHeight()) / Config.tileSize();

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        Tile tile = worldMap.getTile(col, row);
        if (tile == null) {
          continue;
        }

        boolean tileIsSolid = tile.isSolid();

        // Water is solid if no floatable object covers it
        if (tile.getName().equals("water")) {
          tileIsSolid = true;
        }

        if (tileIsSolid) {
          Collider tileCollider = new Collider(col * Config.tileSize(), row * Config.tileSize(),
              Config.tileSize(), Config.tileSize());

          if (collider.intersects(tileCollider)) {
            return tile;
          }
        }
      }
    }

    return null;
  }

  /**
   * Checks if a collider intersects any world objects in the world.
   *
   * @param collider the collider
   * @return the colliding world object, or null if none
   */
  public WorldObject getCollidingObject(Collider collider) {
    for (WorldObject obj : worldObjectManager.getAll()) {
      // Skip non-solid objects
      if (!obj.isSolid()) {
        continue;
      }

      if (collider.intersects(obj.getCollider())) {
        return obj;
      }
    }
    return null;
  }

  /**
   * Gets the world object in front of a collider.
   *
   * @param collider the collider
   * @param direction the direction to check
   * @return the world object in front of the collider, or null if none
   */
  public WorldObject getObjectInFront(Collider collider, Direction direction, int range) {
    for (WorldObject obj : worldObjectManager.getAll()) {
      if (collider.isInFrontOf(obj.getCollider(), direction, range)) {
        return obj;
      }
    }
    return null;
  }

  /**
   * Checks if collider is on a world object.
   *
   * @param collider the collider
   * @param objectType the world object type
   * @return true if the collider is on the world object, false otherwise
   */
  public boolean isOnObject(Collider collider, WorldObjectType objectType) {
    for (WorldObject obj : worldObjectManager.getAll()) {
      if (obj.getType() == objectType) {
        if (obj.getCollider().contains(collider)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check if collider is on a tile.
   *
   * @param collider the collider
   * @param row the row of the tile
   * @param col the column of the tile
   * @return true if collider is on the tile, false otherwise
   */
  public boolean isOnTile(Collider collider, int row, int col) {
    int startCol = collider.getX() / Config.tileSize();
    int startRow = collider.getY() / Config.tileSize();
    int endCol = (collider.getX() + collider.getWidth() - 1) / Config.tileSize();
    int endRow = (collider.getY() + collider.getHeight() - 1) / Config.tileSize();

    for (int i = startRow; i <= endRow; i++) {
      for (int j = startCol; j <= endCol; j++) {
        // Only include valid map positions
        if (j >= 0 && j < Config.mapWidth() && i >= 0 && i < Config.mapHeight()) {
          if (i == row && j == col) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * Resets the collision count to zero.
   */
  public void resetCollisionCount() {
    collisionCount = 0;
  }

  /**
   * Gets the number of collisions that have occurred.
   */
  public int getCollisionCount() {
    return collisionCount;
  }
}
