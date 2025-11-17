package io.tidalisland.worldobjects;

import io.tidalisland.entities.Player;

/**
 * Represents an object that can be touched.
 */
public interface Touchable {
  /**
   * Called when the object is touched.
   */
  void onTouch(Player player);
}
