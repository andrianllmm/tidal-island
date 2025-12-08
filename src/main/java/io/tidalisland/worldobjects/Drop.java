package io.tidalisland.worldobjects;

import io.tidalisland.items.Item;

/**
 * Represents a drop descriptor.
 */
public class Drop {
  private final Item item;
  private final int quantity;

  public Drop(Item item, int quantity) {
    this.item = item;
    this.quantity = quantity;
  }

  public Item getItem() {
    return item;
  }

  public int getQuantity() {
    return quantity;
  }
}
