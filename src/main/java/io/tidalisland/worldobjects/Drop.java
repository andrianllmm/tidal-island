package io.tidalisland.worldobjects;

/**
 * Represents a drop descriptor.
 */
public class Drop {
  private final String type;
  private final int quantity;

  public Drop(String type, int quantity) {
    this.type = type;
    this.quantity = quantity;
  }

  public String getType() {
    return type;
  }

  public int getQuantity() {
    return quantity;
  }
}
