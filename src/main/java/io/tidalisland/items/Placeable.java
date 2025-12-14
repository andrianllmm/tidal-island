package io.tidalisland.items;

import io.tidalisland.entities.Player;
import io.tidalisland.worldobjects.WorldObjectManager;

/**
 * Represents an item that can be placed.
 */
public interface Placeable {
  public boolean place(WorldObjectManager worldObjectManager, Player player);
}
