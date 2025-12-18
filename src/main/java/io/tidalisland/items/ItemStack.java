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
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    this.item = item;
    this.quantity = quantity;
  }


  /**
   * Adds a valid amount of items to the stack.
   *
   * @param amount the amount to add
   * @return the amount added
   */
  public int add(int amount) {
    int toAdd = Math.min(item.getMaxStackSize() - quantity, amount);
    quantity += toAdd;
    return toAdd;
  }

  /**
   * Removes a valid amount of items from the stack.
   *
   * @param amount the amount to remove
   * @return the amount removed
   */
  public int remove(int amount) {
    int toRemove = Math.min(quantity, amount);
    quantity -= toRemove;
    return toRemove;
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
}
