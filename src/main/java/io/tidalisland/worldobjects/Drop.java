package io.tidalisland.worldobjects;

/**
 * Represents a drop descriptor.
 */
public class Drop {
  private final String itemId;
  private final int quantity;

  public Drop(String itemId, int quantity) {
    this.itemId = itemId;
    this.quantity = quantity;
  }

  public String getItemId() {
    return itemId;
  }

  public int getQuantity() {
    return quantity;
  }
}
