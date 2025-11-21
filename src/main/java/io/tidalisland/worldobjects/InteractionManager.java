package io.tidalisland.worldobjects;

import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.collision.Collider;
import io.tidalisland.entities.Entity;
import io.tidalisland.entities.Player;
import io.tidalisland.utils.Position;

/**
 * Manages interactions between entities and world objects.
 */
public class InteractionManager {
  private final WorldObjectManager worldObjectManager;

  public InteractionManager(WorldObjectManager wom) {
    this.worldObjectManager = wom;
  }

  /**
   * Checks if an entity can interact with a world object.
   */
  public void interact(Player player) {
    WorldObject obj = getObjectInFront(player);
    if (obj instanceof Interactable interactable) {
      InteractResult result = interactable.interact(player);

      if (result == null) {
        return;
      }

      if (result.destroyed) {
        worldObjectManager.remove(obj);
      }

      for (Drop drop : result.drops) {
        player.getInventory().add(drop.getItemId(), drop.getQuantity());
      }
    }
  }

  private WorldObject getObjectInFront(Entity entity) {
    Collider interactionZone = entity.getCollider().copy();
    interactionZone.move(entity.getDirection(), entity.getInteractionRange());

    // only check tiles in interactionZone
    int startCol = interactionZone.getX() / TILE_SIZE;
    int startRow = interactionZone.getY() / TILE_SIZE;
    int endCol = (interactionZone.getX() + interactionZone.getWidth() - 1) / TILE_SIZE;
    int endRow = (interactionZone.getY() + interactionZone.getHeight() - 1) / TILE_SIZE;

    for (int row = startRow; row <= endRow; row++) {
      for (int col = startCol; col <= endCol; col++) {
        Position tilePos = new Position(col * TILE_SIZE, row * TILE_SIZE);
        WorldObject obj = worldObjectManager.get(tilePos);

        if (obj instanceof Interactable && interactionZone.intersects(obj.getCollider())) {
          return obj;
        }
      }
    }

    return null;
  }
}
