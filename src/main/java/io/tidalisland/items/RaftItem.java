package io.tidalisland.items;

import io.tidalisland.entities.Player;
import io.tidalisland.graphics.sprites.Sprite;
import io.tidalisland.worldobjects.Raft;
import io.tidalisland.worldobjects.WorldObjectManager;

/**
 * Represents a raft item.
 */
public class RaftItem extends Item implements Placeable {
  /**
   * Creates a new raft item.
   */
  public RaftItem() {
    super("raft", 1);
    this.description = "A raft is a large wooden boat.";
    this.sprite = new Sprite("/sprites/items/raft.png");
  }

  @Override
  public boolean place(WorldObjectManager wom, Player player) {
    Raft raft = new Raft(player.getFacingTile());
    raft.setWorldMap(wom.getWorldMap());
    boolean success = wom.add(raft);
    return success;
  }
}
