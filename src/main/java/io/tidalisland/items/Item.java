package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents an item.
 */
public abstract class Item {
  protected final String type;
  protected String description;
  protected Sprite sprite;

  public Item(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Sprite getSprite() {
    return sprite;
  }
}
