package io.tidalisland.items;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for all items.
 */
public final class ItemRegistry {
  private static final Map<String, Item> ITEMS = new HashMap<>();

  static {
    register(new Wood());
  }

  public static void register(Item item) {
    ITEMS.put(item.getId(), item);
  }

  /**
   * Gets an item by id.
   */
  public static Item get(String itemId) {
    if (!has(itemId)) {
      System.err.println("Item not found: " + itemId);
    }
    return ITEMS.get(itemId);
  }

  public static boolean has(String itemId) {
    return ITEMS.containsKey(itemId);
  }

  public static String view() {
    return ITEMS.toString();
  }
}
