package io.tidalisland.items;

/**
 * Represents a stack of items.
 */
public class ItemStack {
  private final Item item;
  private int quantity;

  /**
   * Creates a new stack of items.
   */
  public ItemStack(Item item, int quantity) {
    if (quantity > item.getMaxStackSize()) {
      throw new IllegalArgumentException("Too many items for stack");
    }
    this.item = item;
    this.quantity = quantity;
  }

  public Item getItem() {
    return item;
  }

  public int getQuantity() {
    return quantity;
  }

  /** Adds a valid amount of items to the stack. */
  public void add(int amount) {
    if (!item.isStackable()) {
      throw new IllegalStateException("Item not stackable");
    }
    if (quantity + amount > item.getMaxStackSize()) {
      throw new IllegalStateException("Stack overflow");
    }
    quantity += amount;
  }

  /** Removes a valid amount of items from the stack. */
  public void remove(int amount) {
    if (amount > quantity) {
      throw new IllegalStateException("Not enough items in stack");
    }
    quantity -= amount;
  }

  public boolean isFull() {
    return quantity >= item.getMaxStackSize();
  }

  public int getRemainingCapacity() {
    return item.getMaxStackSize() - quantity;
  }
}

