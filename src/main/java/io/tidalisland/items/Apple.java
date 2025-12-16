package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * An apple.
 */
public class Apple extends Food {

  /**
   * Creates a new apple.
   */
  public Apple() {
    super("apple", 8, 10);
    this.description = "A delicious fruit.";
    this.sprite = new Sprite("/sprites/items/apple.png");
  }
}
