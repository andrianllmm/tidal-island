package io.tidalisland.inventory;

import io.tidalisland.events.EventListener;
import io.tidalisland.events.InventoryChangeEvent;
import io.tidalisland.events.Observable;
import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the player's inventory.
 */
public class Inventory implements Observable<InventoryChangeEvent> {
  /** Map of item types to stacks of items for fast lookup. */
  private final Map<String, List<ItemStack<? extends Item>>> items = new HashMap<>();
  private final int maxSlots;
  private final CopyOnWriteArrayList<EventListener<InventoryChangeEvent>> listeners =
      new CopyOnWriteArrayList<>();

  /**
   * Creates a new inventory.
   */
  public Inventory(int maxSlots) {
    if (maxSlots < 1) {
      throw new IllegalArgumentException("Max slots must be at least 1");
    }
    this.maxSlots = maxSlots;
  }

  /**
   * Adds an item to the inventory.
   */
  public boolean add(Item item, int amount) {
    if (amount <= 0) {
      return false;
    }

    List<ItemStack<? extends Item>> stacks =
        items.computeIfAbsent(item.getType(), k -> new ArrayList<>());
    int remaining = amount;

    // Fill existing stacks
    for (ItemStack<? extends Item> stack : stacks) {
      if (!stack.isFull()) {
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
    int freeSlots = maxSlots - getUsedSlots();
    int neededSlots = (int) Math.ceil((double) remaining / item.getMaxStackSize());
    if (neededSlots > freeSlots) {
      return false; // Not enough slots for new stacks
    }

    // Create new stacks as needed
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
   */
  public boolean remove(Item item, int amount) {
    if (amount <= 0) {
      return false;
    }

    if (amount > getQuantity(item)) {
      amount = getQuantity(item);
    }

    List<ItemStack<? extends Item>> stacks = items.get(item.getType());
    if (stacks == null) {
      return false;
    }

    int remaining = amount;

    // Remove from existing stacks
    for (ItemStack<? extends Item> stack : stacks) {
      if (remaining <= 0) {
        break;
      }
      int toRemove = Math.min(stack.getQuantity(), remaining);
      stack.remove(toRemove);
      remaining -= toRemove;
    }

    // Remove empty stacks
    stacks.removeIf(stack -> stack.getQuantity() == 0);

    // Remove item from the map if none are left
    if (stacks.isEmpty()) {
      items.remove(item.getType());
    }

    // Fail if not enough was removed
    if (remaining != 0) {
      return false;
    }

    emitChange(item, amount, false);
    return true;
  }

  /** Returns the quantity of an item in the inventory. */
  public int getQuantity(String itemType) {
    List<ItemStack<? extends Item>> stacks = items.get(itemType);
    if (stacks == null) {
      return 0;
    }
    return stacks.stream().mapToInt(ItemStack::getQuantity).sum();
  }

  public int getQuantity(Item item) {
    return getQuantity(item.getType());
  }

  public int getMaxSlots() {
    return maxSlots;
  }

  public int getUsedSlots() {
    return items.values().stream().mapToInt(List::size).sum();
  }

  public int size() {
    return items.size();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public Set<String> getItems() {
    return items.keySet();
  }

  /** Returns the stacks of items in the inventory. */
  public List<ItemStack<? extends Item>> getStacks() {
    List<ItemStack<? extends Item>> stacks = new ArrayList<>();
    for (Map.Entry<String, List<ItemStack<? extends Item>>> entry : items.entrySet()) {
      for (ItemStack<? extends Item> stack : entry.getValue()) {
        stacks.add(stack);
      }
    }
    return stacks;
  }

  /** Returns the summary of the inventory (item type -> total quantity). */
  public Map<String, Integer> getSummary() {
    Map<String, Integer> summary = new HashMap<>();
    for (Map.Entry<String, List<ItemStack<? extends Item>>> entry : items.entrySet()) {
      int total = entry.getValue().stream().mapToInt(ItemStack::getQuantity).sum();
      summary.put(entry.getKey(), total);
    }
    return summary;
  }

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
