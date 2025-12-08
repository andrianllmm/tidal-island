package io.tidalisland.inventory;

import io.tidalisland.items.Item;
import io.tidalisland.items.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents the player's inventory.
 */
public class Inventory {
  /** Map of item types to stacks of items for fast lookup. */
  private final Map<String, List<ItemStack>> items = new HashMap<>(); // item type -> list of stacks
  private boolean dirty = false;

  /**
   * Adds an item to the inventory.
   */
  public boolean add(Item item, int amount) {
    if (amount <= 0) {
      return false;
    }

    List<ItemStack> stacks = items.computeIfAbsent(item.getType(), k -> new ArrayList<>());
    int remaining = amount;

    // Fill existing stacks
    for (ItemStack stack : stacks) {
      if (!stack.isFull()) {
        int toAdd = Math.min(stack.getRemainingCapacity(), remaining);
        stack.add(toAdd);
        remaining -= toAdd;
        if (remaining == 0) {
          dirty = true;
          return true;
        }
      }
    }

    // Create new stacks as needed
    while (remaining > 0) {
      int toAdd = Math.min(item.getMaxStackSize(), remaining);
      stacks.add(new ItemStack(item, toAdd));
      remaining -= toAdd;
    }

    dirty = true;
    return true;
  }

  /**
   * Removes an item from the inventory.
   */
  public boolean remove(Item item, int amount) {
    if (amount <= 0) {
      return false;
    }

    List<ItemStack> stacks = items.get(item.getType());
    if (stacks == null) {
      return false;
    }

    int remaining = amount;

    // Remove from existing stacks
    for (ItemStack stack : stacks) {
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

    dirty = true;
    return true;
  }

  /** Returns the quantity of an item in the inventory. */
  public int getQuantity(Item item) {
    List<ItemStack> stacks = items.get(item.getType());
    if (stacks == null) {
      return 0;
    }
    return stacks.stream().mapToInt(ItemStack::getQuantity).sum();
  }

  public boolean has(Item item, int amount) {
    return getQuantity(item) >= amount;
  }

  public boolean has(Item item) {
    return getQuantity(item) > 0;
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
  public List<ItemStack> getStacks() {
    List<ItemStack> stacks = new ArrayList<>();
    for (Map.Entry<String, List<ItemStack>> entry : items.entrySet()) {
      for (ItemStack stack : entry.getValue()) {
        stacks.add(stack);
      }
    }
    return stacks;
  }

  /** Returns the summary of the inventory (item type -> total quantity). */
  public Map<String, Integer> getSummary() {
    Map<String, Integer> summary = new HashMap<>();
    for (Map.Entry<String, List<ItemStack>> entry : items.entrySet()) {
      int total = entry.getValue().stream().mapToInt(ItemStack::getQuantity).sum();
      summary.put(entry.getKey(), total);
    }
    return summary;
  }

  /** Checks if the inventory has been modified. */
  public boolean isDirty() {
    return dirty;
  }

  /** Clears the dirty flag. */
  public boolean clearDirty() {
    boolean old = dirty;
    dirty = false;
    return old;
  }

  @Override
  public String toString() {
    return getSummary().toString();
  }
}
