package io.tidalisland.items;

/**
 * Represents an item.
 */
public abstract class Item {
  protected String id;
  protected String description;

  public Item(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }
}
