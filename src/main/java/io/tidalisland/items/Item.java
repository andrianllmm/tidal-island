package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents an item in the game.
 */
public abstract class Item {

  protected final String type;
  protected final int maxStackSize; // >1 for stackable items, 1 otherwise
  protected String description;
  protected Sprite sprite;

  /**
   * Creates a new item.
   *
   * @param type the type
   * @param maxStackSize the max stack size
   * @throws IllegalArgumentException if max stack size is less than 1
   */
  public Item(String type, int maxStackSize) {
    this.type = type;
    if (maxStackSize < 1) {
      throw new IllegalArgumentException("Max stack size must be greater than 0");
    }
    this.maxStackSize = maxStackSize;
  }

  public String getType() {
    return type;
  }

  public int getMaxStackSize() {
    return maxStackSize;
  }

  public boolean isStackable() {
    return maxStackSize > 1;
  }

  public String getDescription() {
    return description;
  }

  public Sprite getSprite() {
    return sprite;
  }
}
