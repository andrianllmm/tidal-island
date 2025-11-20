package io.tidalisland.items;

/**
 * Represents an item.
 */
public abstract class Item {
  protected String type;
  protected String description;

  public Item(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }
}
