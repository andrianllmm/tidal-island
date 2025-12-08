package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a wood item.
 */
public class Wood extends Item {
  /**
   * Creates a new wood item.
   */
  public Wood() {
    super("wood", 8);
    this.description = "Wood is a common material.";
    this.sprite = new Sprite("/sprites/items/wood.png");
  }
}
