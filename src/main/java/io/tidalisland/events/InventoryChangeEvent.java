package io.tidalisland.events;

import io.tidalisland.items.Item;

/**
 * Event triggered when an item is added or removed from the inventory.
 */
public class InventoryChangeEvent implements Event {

  private final Item item;
  private final int amount;
  private final boolean added;

  /** Creates a new inventory change event. */
  public InventoryChangeEvent(Item item, int amount, boolean added) {
    this.item = item;
    this.amount = amount;
    this.added = added;
  }

  public Item getItem() {
    return item;
  }

  public int getAmount() {
    return amount;
  }

  public boolean wasAdded() {
    return added;
  }
}
