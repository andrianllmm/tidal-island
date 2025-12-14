package io.tidalisland.worldobjects;

import io.tidalisland.collision.Collider;
import io.tidalisland.entities.Entity;
import io.tidalisland.entities.Player;

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
        player.getInventory().add(drop.getItem(), drop.getQuantity());
      }
    }
  }

  private WorldObject getObjectInFront(Entity entity) {
    Collider playerCollider = entity.getCollider().copy();
    playerCollider.move(entity.getDirection(), entity.getInteractionRange());

    // check all objects
    for (WorldObject obj : worldObjectManager.getAll()) {
      if (obj instanceof Interactable && playerCollider.intersects(obj.getCollider())) {
        return obj;
      }
    }

    return null;
  }
}
