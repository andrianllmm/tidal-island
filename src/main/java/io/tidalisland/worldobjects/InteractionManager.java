package io.tidalisland.worldobjects;

import io.tidalisland.collision.CollisionManager;
import io.tidalisland.entities.Player;
import io.tidalisland.items.Tool;

/**
 * Manages interactions between entities and world objects.
 */
public class InteractionManager {

  private final WorldObjectManager worldObjectManager;
  private final CollisionManager collisionManager;

  public InteractionManager(WorldObjectManager wom, CollisionManager cm) {
    this.worldObjectManager = wom;
    this.collisionManager = cm;
  }

  /**
   * Checks if an entity can interact with a world object.
   */
  public void interact(Player player) {
    WorldObject obj = collisionManager.getObjectInFront(player.getCollider(), player.getDirection(),
        player.getInteractionRange());
    if (obj instanceof Interactable interactable) {
      Tool tool = player.getEquipment().getEquippedTool();
      if (tool != null && tool.isBroken()) {
        player.getEquipment().unequip();
      }

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
}
