package io.tidalisland.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player's inventory.
 */
public class Inventory {
  private final Map<String, Integer> items = new HashMap<>(); // item type -> quantity

  /**
   * Adds an item to the inventory.
   */
  public boolean add(String itemId, int amount) {
    if (amount <= 0) {
      return false;
    }
    items.merge(itemId, amount, Integer::sum);

    System.out.println(this);

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

    System.out.println(this);

    return true;
  }

  public int getQuantity(String itemId) {
    return items.getOrDefault(itemId, 0);
  }

  public boolean has(String itemId, int amount) {
    return getQuantity(itemId) >= amount;
  }

  public Map<String, Integer> view() {
    return Map.copyOf(items);
  }

  @Override
  public String toString() {
    return items.toString();
  }
}
