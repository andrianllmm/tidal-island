package io.tidalisland.items;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * Represents an item.
 */
public abstract class Item {
  protected String id;
  protected String description;
  protected Sprite sprite;

  public Item(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Sprite getSprite() {
    return sprite;
  }
}
