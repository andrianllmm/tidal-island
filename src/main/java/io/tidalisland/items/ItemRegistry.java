package io.tidalisland.items;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Central registry for all items.
 */
public final class ItemRegistry {
  private static final Map<String, Supplier<Item>> items = new HashMap<>();

  private ItemRegistry() {
  }

  static {
    register("wood", Wood::new);
    register("stone", Stone::new);
    register("leaf", Leaf::new);
    register("plank", Plank::new);
    register("raft", RaftItem::new);
  }

  /** Registers an item. */
  public static void register(String id, Supplier<Item> factory) {
    if (items.containsKey(id)) {
      throw new IllegalArgumentException("Item already registered: " + id);
    }
    items.put(id, factory);
  }

  /** Creates an item. */
  public static Item create(String id) {
    Supplier<Item> factory = items.get(id);
    if (factory == null) {
      throw new IllegalArgumentException("Item not found: " + id);
    }
    return factory.get();
  }

  public static boolean has(String id) {
    return items.containsKey(id);
  }

  public static String viewAll() {
    return items.keySet().toString();
  }
}
