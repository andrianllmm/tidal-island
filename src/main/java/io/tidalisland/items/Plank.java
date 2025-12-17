package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a plank.
 */
public class Plank extends Item {

  public static final ItemType TYPE = new ItemType("plank");

  /**
   * Creates a new plank.
   */
  public Plank() {
    super(TYPE, 8);
    this.description = "A plank is a common material.";
    this.sprite = new Sprite("/sprites/items/plank.png");
  }
}
