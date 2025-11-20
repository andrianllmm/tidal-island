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
    ITEMS.put(item.getType(), item);
  }

  public static Item get(String type) {
    return ITEMS.get(type);
  }

  public static boolean has(String type) {
    return ITEMS.containsKey(type);
  }

  public static String view() {
    return ITEMS.toString();
  }
}
