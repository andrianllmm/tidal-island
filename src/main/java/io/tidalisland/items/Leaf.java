package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a leaf item.
 */
public class Leaf extends Item {
  /**
   * Creates a new leaf item.
   */
  public Leaf() {
    super("leaf");
    this.description = "A leaf is a common plant.";
    this.sprite = new Sprite("/sprites/items/leaf.png");
  }
}
