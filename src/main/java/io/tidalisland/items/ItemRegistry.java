package io.tidalisland.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Central registry for all {@link Item}s.
 */
public final class ItemRegistry {

  private static final Map<String, Supplier<Item>> items = new HashMap<>();

  private ItemRegistry() {}

  static {
    register("wood", Wood::new);
    register("stone", Stone::new);
    register("leaf", Leaf::new);
    register("plank", Plank::new);
    register("raft", RaftItem::new);
  }

  /**
   * Registers an item.
   *
   * @param id the item id
   * @param factory the item factory
   */
  public static void register(String id, Supplier<Item> factory) {
    if (items.containsKey(id)) {
      throw new IllegalArgumentException("Item already registered: " + id);
    }
    items.put(id, factory);
  }

  /**
   * Creates an item.
   *
   * @param id the item id
   * @return the created item
   */
  public static Item create(String id) {
    Supplier<Item> factory = items.get(id);
    if (factory == null) {
      throw new IllegalArgumentException("Item not found: " + id);
    }
    return factory.get();
  }

  /**
   * Gets all item ids.
   *
   * @return a list of item ids
   */
  public static List<String> getAllIds() {
    return new ArrayList<>(items.keySet());
  }

  /**
   * Checks if an item is registered.
   *
   * @param id the item id
   * @return true if the item is registered, false otherwise
   */
  public static boolean has(String id) {
    return items.containsKey(id);
  }
}
