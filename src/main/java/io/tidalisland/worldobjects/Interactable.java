package io.tidalisland.worldobjects;

import io.tidalisland.entities.Player;

/**
 * Represents an object that can be interacted with.
 */
public interface Interactable {
  /**
   * Called when the object is interacted with.
   */
  InteractResult interact(Player player);
}
