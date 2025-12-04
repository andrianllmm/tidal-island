package io.tidalisland.worldobjects;

/**
 * Represents a drop descriptor.
 */
public class Drop {
  private final String itemType;
  private final int quantity;

  public Drop(String itemType, int quantity) {
    this.itemType = itemType;
    this.quantity = quantity;
  }

  public String getItemType() {
    return itemType;
  }

  public int getQuantity() {
    return quantity;
  }
}
