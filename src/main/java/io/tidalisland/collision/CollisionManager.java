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
    int startCol = collider.getX() / Config.tileSize();
    int startRow = collider.getY() / Config.tileSize();
    int endCol = (collider.getX() + collider.getWidth() - 1) / Config.tileSize();
    int endRow = (collider.getY() + collider.getHeight() - 1) / Config.tileSize();

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        Tile tile = worldMap.getTile(col, row);

        if (tile != null && tile.isSolid()) {
          // Create tile collider
          Collider tileCollider = new Collider(col * Config.tileSize(), row * Config.tileSize(),
              Config.tileSize(), Config.tileSize());

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
    int startCol = collider.getX() / Config.tileSize();
    int startRow = collider.getY() / Config.tileSize();
    int endCol = (collider.getX() + collider.getWidth() - 1) / Config.tileSize();
    int endRow = (collider.getY() + collider.getHeight() - 1) / Config.tileSize();

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        Position pos = new Position(col * Config.tileSize(), row * Config.tileSize());
        WorldObject obj = worldObjectManager.get(pos);

        if (obj != null && collider.intersects(obj.getCollider())) {
          return obj;
        }
      }
    }

    return null;
  }
}
