package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a raft item.
 */
public class RaftItem extends Item {
  /**
   * Creates a new raft item.
   */
  public RaftItem() {
    super("raft", 1);
    this.description = "A raft is a large wooden boat.";
    this.sprite = new Sprite("/sprites/items/raft.png");
  }
}
