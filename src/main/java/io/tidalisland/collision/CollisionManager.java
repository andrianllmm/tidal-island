package io.tidalisland.collision;

import io.tidalisland.config.Config;
import io.tidalisland.entities.Entity;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.WorldObject;
import io.tidalisland.worldobjects.WorldObjectManager;

/**
 * Manages collisions between entities and the world.
 */
public class CollisionManager {
  private final WorldMap worldMap;
  private final WorldObjectManager worldObjectManager;
  private int collisionCount;

  public CollisionManager(WorldMap worldMap, WorldObjectManager worldObjectManager) {
    this.worldMap = worldMap;
    this.worldObjectManager = worldObjectManager;
  }

  /**
   * Checks if an entity can move to a new position without colliding.
   */
  public boolean canMove(Entity entity, int nextX, int nextY) {
    // Create a hypothetical collider at the new position
    Collider future = entity.getCollider().copy();
    future.updatePosition(nextX, nextY);

    // Check collision with world tiles
    if (collidesWithTiles(future)) {
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
   */
  public boolean canMove(Entity entity, Position nextPosition) {
    return canMove(entity, nextPosition.getX(), nextPosition.getY());
  }

  public void resetCollisionCount() {
    collisionCount = 0;
  }

  public int getCollisionCount() {
    return collisionCount;
  }

  /**
   * Checks if a collider intersects any solid tiles in the world.
   */
  private boolean collidesWithTiles(Collider collider) {
    // Check if the player is standing on any floatable object
    for (WorldObject obj : worldObjectManager.getAll()) {
      if (obj.isFloatable() && obj.getCollider().contains(collider)) {
        return false;
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
          Collider tileCollider = new Collider(
              col * Config.tileSize(),
              row * Config.tileSize(),
              Config.tileSize(),
              Config.tileSize());

          if (collider.intersects(tileCollider)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * Checks if a collider intersects any world objects in the world.
   */
  private WorldObject getCollidingObject(Collider collider) {
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
}
