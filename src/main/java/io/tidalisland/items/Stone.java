package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a stone item.
 */
public class Stone extends Item {

  /**
   * Creates a stone item.
   */
  public Stone() {
    super("stone");
    this.description = "Stone is a common material.";
    this.sprite = new Sprite("/sprites/items/stone.png");
  }
}
