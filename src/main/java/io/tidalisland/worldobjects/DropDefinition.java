package io.tidalisland.worldobjects;

import static io.tidalisland.config.Config.random;

/**
 * Represents a drop definition.
 */
public class DropDefinition {
  private final String itemId;
  private final int minQuantity;
  private final int maxQuantity;

  /** Creates a new drop definition with a random quantity. */
  public DropDefinition(String itemId, int minQuantity, int maxQuantity) {
    this.itemId = itemId;
    this.minQuantity = minQuantity;
    this.maxQuantity = maxQuantity;
  }

  /** Creates a new drop definition with a fixed quantity. */
  public DropDefinition(String itemId, int quantity) {
    this(itemId, quantity, quantity);
  }

  /** Generates a drop. */
  public Drop generate() {
    int qty;
    if (minQuantity == maxQuantity) {
      qty = minQuantity;
    } else {
      qty = minQuantity + random().nextInt(maxQuantity - minQuantity + 1);
    }
    return new Drop(itemId, qty);
  }
}
