package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents a stone item.
 */
public class Stone extends Item {

  public static final ItemType TYPE = new ItemType("stone");

  /**
   * Creates a stone item.
   */
  public Stone() {
    super(TYPE, 16);
    this.description = "Stone is a common material.";
    this.sprite = new Sprite("/sprites/items/stone.png");
  }
}
