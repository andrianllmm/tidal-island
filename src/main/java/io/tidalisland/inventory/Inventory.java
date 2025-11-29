package io.tidalisland.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents the player's inventory.
 */
public class Inventory {
  private final Map<String, Integer> items = new HashMap<>(); // item id -> quantity
  private boolean dirty = false;

  /**
   * Adds an item to the inventory.
   */
  public boolean add(String itemId, int amount) {
    if (amount <= 0) {
      return false;
    }
    items.merge(itemId, amount, Integer::sum);

    dirty = true;
    return true;
  }

  /**
   * Removes an item from the inventory.
   */
  public boolean remove(String itemId, int amount) {
    if (!items.containsKey(itemId) || amount <= 0) {
      return false;
    }

    int current = items.get(itemId);
    if (current < amount) {
      return false;
    }

    int updated = current - amount;
    if (updated == 0) {
      items.remove(itemId);
    } else {
      items.put(itemId, updated);
    }

    dirty = true;
    return true;
  }

  public int getQuantity(String itemId) {
    return items.getOrDefault(itemId, 0);
  }

  public boolean has(String itemId, int amount) {
    return getQuantity(itemId) >= amount;
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

  public Map<String, Integer> view() {
    return Map.copyOf(items);
  }

  /**
   * Checks if the inventory has been modified.
   */
  public boolean isDirty() {
    return dirty;
  }

  /**
   * Clears the dirty flag.
   */
  public boolean clearDirty() {
    boolean old = dirty;
    dirty = false;
    return old;
  }

  @Override
  public String toString() {
    return items.toString();
  }
}
