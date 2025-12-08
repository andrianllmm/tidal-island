package io.tidalisland.worldobjects;

import static io.tidalisland.config.Config.random;

import io.tidalisland.items.Item;

/**
 * Represents a drop definition.
 */
public class DropDefinition {
  private final Item item;
  private final int minQuantity;
  private final int maxQuantity;

  /** Creates a new drop definition with a random quantity. */
  public DropDefinition(Item item, int minQuantity, int maxQuantity) {
    this.item = item;
    this.minQuantity = minQuantity;
    this.maxQuantity = maxQuantity;
  }

  /** Creates a new drop definition with a fixed quantity. */
  public DropDefinition(Item item, int quantity) {
    this(item, quantity, quantity);
  }

  /** Generates a drop. */
  public Drop generate() {
    int qty;
    if (minQuantity == maxQuantity) {
      qty = minQuantity;
    } else {
      qty = minQuantity + random().nextInt(maxQuantity - minQuantity + 1);
    }
    return new Drop(item, qty);
  }
}
