package io.tidalisland.worldobjects;

import static io.tidalisland.config.Config.random;

import io.tidalisland.items.Item;

/**
 * Represents an item drop.
 */
public class Drop {
  private final Item item;
  private final int minQuantity;
  private final int maxQuantity;

  /** Fixed quantity drop. */
  public Drop(Item item, int quantity) {
    this(item, quantity, quantity);
  }

  /** Drop with random quantity between min and max. */
  public Drop(Item item, int minQuantity, int maxQuantity) {
    this.item = item;
    this.minQuantity = minQuantity;
    this.maxQuantity = maxQuantity;
  }

  /** Returns the actual quantity for this drop. */
  public int getQuantity() {
    if (minQuantity == maxQuantity) {
      return minQuantity;
    }
    return minQuantity + random().nextInt(maxQuantity - minQuantity + 1);
  }

  public Item getItem() {
    return item;
  }
}
