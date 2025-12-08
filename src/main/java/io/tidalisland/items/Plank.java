package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a plank.
 */
public class Plank extends Item {
  /**
   * Creates a new plank.
   */
  public Plank() {
    super("plank", 8);
    this.description = "A plank is a common material.";
    this.sprite = new Sprite("/sprites/items/plank.png");
  }
}
