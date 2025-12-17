package io.tidalisland.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Central registry for all {@link Item}s. Uses {@link ItemType} as the canonical key, with string
 * IDs for serialization and external data support.
 */
public final class ItemRegistry {

  /** Maps ItemType -> item factory. */
  private static final Map<ItemType, Supplier<Item>> items = new HashMap<>();

  /** Maps string ID -> ItemType for serialization/deserialization. */
  private static final Map<String, ItemType> idToType = new HashMap<>();

  private ItemRegistry() {}

  static {
    register(Wood.TYPE, Wood::new);
    register(Stone.TYPE, Stone::new);
    register(Leaf.TYPE, Leaf::new);
    register(Plank.TYPE, Plank::new);
    register(Axe.TYPE, Axe::new);
    register(RaftItem.TYPE, RaftItem::new);
  }

  /**
   * Registers an item.
   *
   * @param type the canonical item type
   * @param factory factory to create new item instances
   */
  public static void register(ItemType type, Supplier<Item> factory) {
    if (items.containsKey(type)) {
      throw new IllegalArgumentException("Item already registered: " + type);
    }
    items.put(type, factory);

    String id = type.id().toLowerCase();
    if (idToType.containsKey(id)) {
      throw new IllegalArgumentException("Item ID already registered: " + id);
    }
    idToType.put(id, type);
  }

  /**
   * Creates an item by its canonical ItemType.
   *
   * @param type the item type
   * @return a new Item instance
   */
  public static Item create(ItemType type) {
    Supplier<Item> factory = items.get(type);
    if (factory == null) {
      throw new IllegalArgumentException("Item not found: " + type);
    }
    return factory.get();
  }

  /**
   * Creates an item by its string ID (for JSON or external data).
   *
   * @param id the string ID
   * @return a new Item instance
   */
  public static Item create(String id) {
    ItemType type = idToType.get(id.toLowerCase());
    if (type == null) {
      throw new IllegalArgumentException("Item not found: " + id);
    }
    return create(type);
  }

  /**
   * Checks if an item is registered by ItemType.
   *
   * @param type the item type
   * @return true if registered
   */
  public static boolean has(ItemType type) {
    return items.containsKey(type);
  }

  /**
   * Checks if an item is registered by string ID.
   *
   * @param id the string ID
   * @return true if registered
   */
  public static boolean has(String id) {
    return idToType.containsKey(id.toLowerCase());
  }

  /**
   * Gets all registered canonical ItemTypes.
   *
   * @return unmodifiable list of ItemTypes
   */
  public static List<ItemType> getAllTypes() {
    return Collections.unmodifiableList(new ArrayList<>(items.keySet()));
  }

  /**
   * Gets all string IDs of registered items.
   *
   * @return unmodifiable list of IDs
   */
  public static List<String> getAllIds() {
    return Collections.unmodifiableList(new ArrayList<>(idToType.keySet()));
  }

  /**
   * Converts a string ID to its canonical ItemType.
   *
   * @param id string ID
   * @return ItemType
   */
  public static ItemType getTypeById(String id) {
    ItemType type = idToType.get(id.toLowerCase());
    if (type == null) {
      throw new IllegalArgumentException("Unknown item ID: " + id);
    }
    return type;
  }

  /**
   * Converts an ItemType to its string ID.
   *
   * @param type ItemType
   * @return string ID
   */
  public static String getId(ItemType type) {
    return type.id();
  }
}
