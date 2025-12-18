package io.tidalisland.inventory;

import io.tidalisland.events.EventListener;
import io.tidalisland.events.InventoryChangeEvent;
import io.tidalisland.events.Observable;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import io.tidalisland.items.ItemType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Represents an inventory.
 */
public class Inventory implements Observable<InventoryChangeEvent> {

  /** Flat list of item stacks representing inventory slots. */
  private final List<ItemStack<? extends Item>> stacks = new ArrayList<>();

  private final int maxSlots;
  private final CopyOnWriteArrayList<EventListener<InventoryChangeEvent>> listeners =
      new CopyOnWriteArrayList<>();

  /**
   * Creates a new inventory with the specified max slots.
   *
   * @param maxSlots the max slots
   * @throws IllegalArgumentException if max slots is less than 1
   */
  public Inventory(int maxSlots) {
    if (maxSlots < 1) {
      throw new IllegalArgumentException("Max slots must be at least 1");
    }
    this.maxSlots = maxSlots;
  }

  /**
   * Adds an item to the inventory.
   *
   * @param item the item
   * @param amount the amount
   * @return true if the item was added, false otherwise
   */
  public boolean add(Item item, int amount) {
    if (amount <= 0) {
      return false;
    }

    int remaining = amount;

    // Fill existing stacks of the same type
    for (ItemStack<? extends Item> stack : stacks) {
      if (stack.getItem().getType().equals(item.getType()) && !stack.isFull()) {
        int toAdd = Math.min(stack.getRemainingCapacity(), remaining);
        stack.add(toAdd);
        remaining -= toAdd;
        if (remaining == 0) {
          emitChange(item, amount, true);
          return true;
        }
      }
    }

    // Calculate free slots
    int freeSlots = maxSlots - stacks.size();
    int neededSlots = (int) Math.ceil((double) remaining / item.getMaxStackSize());
    if (neededSlots > freeSlots) {
      return false;
    }

    // Create new stacks
    while (remaining > 0) {
      int toAdd = Math.min(item.getMaxStackSize(), remaining);
      stacks.add(new ItemStack<>(item, toAdd));
      remaining -= toAdd;
    }

    emitChange(item, amount, true);
    return true;
  }

  /**
   * Removes an item from the inventory.
   *
   * @param item the item
   * @param amount the amount
   * @return true if the item was removed, false otherwise
   */
  public boolean remove(Item item, int amount) {
    if (amount <= 0) {
      return false;
    }

    int available = getQuantity(item);
    if (available == 0) {
      return false;
    }

    amount = Math.min(amount, available);
    int remaining = amount;

    for (ItemStack<? extends Item> stack : stacks) {
      if (remaining <= 0) {
        break;
      }
      if (stack.getItem().getType().equals(item.getType())) {
        int toRemove = Math.min(stack.getQuantity(), remaining);
        stack.remove(toRemove);
        remaining -= toRemove;
      }
    }

    // Remove empty stacks
    stacks.removeIf(stack -> stack.getQuantity() == 0);

    if (remaining != 0) {
      return false;
    }

    emitChange(item, amount, false);
    return true;
  }

  /**
   * Checks if the inventory has an item of type.
   *
   * @param type the item type
   * @return true if the inventory has the item, false otherwise
   */
  public boolean has(ItemType type) {
    return stacks.stream().anyMatch(s -> s.getItem().getType().equals(type));
  }

  /**
   * Checks if the inventory has an item.
   *
   * @param item the item
   * @return true if the inventory has the item, false otherwise
   */
  public boolean has(Item item) {
    return has(item.getType());
  }

  /**
   * Gets the quantity of an item in the inventory.
   *
   * @param type the item type
   * @return the quantity
   */
  public int getQuantity(ItemType type) {
    return stacks.stream().filter(s -> s.getItem().getType().equals(type))
        .mapToInt(ItemStack::getQuantity).sum();
  }

  public int getQuantity(Item item) {
    return getQuantity(item.getType());
  }

  public int getMaxSlots() {
    return maxSlots;
  }

  public int getUsedSlots() {
    return stacks.size();
  }

  public int size() {
    return stacks.size();
  }

  public boolean isEmpty() {
    return stacks.isEmpty();
  }

  /**
   * Gets the types of items in the inventory.
   *
   * @return a set of item types
   */
  public Set<ItemType> getItemTypes() {
    return stacks.stream().map(s -> s.getItem().getType()).collect(Collectors.toSet());
  }

  /**
   * Gets the stacks of items in the inventory.
   *
   * @return a list of item stacks
   */
  public List<ItemStack<? extends Item>> getStacks() {
    return new ArrayList<>(stacks);
  }

  /**
   * Gets the summary of the inventory.
   *
   * @return a map of item type -> total quantity
   */
  public Map<ItemType, Integer> getSummary() {
    Map<ItemType, Integer> summary = new HashMap<>();
    for (ItemStack<? extends Item> stack : stacks) {
      ItemType type = stack.getItem().getType();
      summary.merge(type, stack.getQuantity(), Integer::sum);
    }
    return summary;
  }

  /** Emits an inventory change event. */
  private void emitChange(Item item, int amount, boolean added) {
    dispatch(new InventoryChangeEvent(item, amount, added), listeners);
  }

  @Override
  public void addListener(EventListener<InventoryChangeEvent> listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(EventListener<InventoryChangeEvent> listener) {
    listeners.remove(listener);
  }

  @Override
  public String toString() {
    return getSummary().toString();
  }
}
