package io.tidalisland.collision;

import static io.tidalisland.config.Config.TILE_SIZE;

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
      return false;
    }

    // Check collision with world objects
    WorldObject worldObj = getCollidingObject(future);
    if (worldObj != null && worldObj.isSolid()) {
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

  /**
   * Checks if a collider intersects any solid tiles in the world.
   */
  private boolean collidesWithTiles(Collider collider) {
    int startCol = collider.getX() / TILE_SIZE;
    int startRow = collider.getY() / TILE_SIZE;
    int endCol = (collider.getX() + collider.getWidth() - 1) / TILE_SIZE;
    int endRow = (collider.getY() + collider.getHeight() - 1) / TILE_SIZE;

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        Tile tile = worldMap.getTile(col, row);

        if (tile != null && tile.isSolid()) {
          // Create tile collider
          Collider tileCollider =
              new Collider(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

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
    int startCol = collider.getX() / TILE_SIZE;
    int startRow = collider.getY() / TILE_SIZE;
    int endCol = (collider.getX() + collider.getWidth() - 1) / TILE_SIZE;
    int endRow = (collider.getY() + collider.getHeight() - 1) / TILE_SIZE;

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        Position pos = new Position(col * TILE_SIZE, row * TILE_SIZE);
        WorldObject obj = worldObjectManager.get(pos);

        if (obj != null && collider.intersects(obj.getCollider())) {
          return obj;
        }
      }
    }

    return null;
  }

}
