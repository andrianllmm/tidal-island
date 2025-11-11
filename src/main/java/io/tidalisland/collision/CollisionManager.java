package io.tidalisland.collision;

import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.entities.Entity;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;

/**
 * Manages collisions between entities and the world.
 */
public class CollisionManager {

  private final WorldMap worldMap;

  public CollisionManager(WorldMap worldMap) {
    this.worldMap = worldMap;
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
}
