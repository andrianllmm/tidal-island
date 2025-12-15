package io.tidalisland.items;

/**
 * Represents a stack of items.
 *
 * @param <T> the type of item this stack holds
 */
public class ItemStack<T extends Item> {

  private final T item;
  private int quantity;

  /**
   * Creates a new stack of items.
   */
  public ItemStack(T item, int quantity) {
    if (quantity > item.getMaxStackSize()) {
      throw new IllegalArgumentException("Too many items for stack");
    }
    this.item = item;
    this.quantity = quantity;
  }

  /**
   * Returns the item in this stack.
   */
  public T getItem() {
    return item;
  }

  /**
   * Returns the current quantity in this stack.
   */
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

  /**
   * Returns true if the stack is full.
   */
  public boolean isFull() {
    return quantity >= item.getMaxStackSize();
  }

  /**
   * Returns the remaining capacity in this stack.
   */
  public int getRemainingCapacity() {
    return item.getMaxStackSize() - quantity;
  }
}
