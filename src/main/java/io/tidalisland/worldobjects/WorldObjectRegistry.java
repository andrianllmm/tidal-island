package io.tidalisland.worldobjects;

import io.tidalisland.utils.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Central registry for all world object types.
 */
public final class WorldObjectRegistry {
  private static final Map<String, Function<Position, WorldObject>> objects = new HashMap<>();

  private WorldObjectRegistry() {}

  static {
    register("tree", Tree::new);
    register("rock", Rock::new);
    register("bush", Bush::new);
  }

  /**
   * Registers an world object type.
   */
  public static void register(String id, Function<Position, WorldObject> factory) {
    if (objects.containsKey(id)) {
      throw new IllegalArgumentException("WorldObject already registered: " + id);
    }
    objects.put(id, factory);
  }

  /**
   * Creates a world object a[t the given position.
   */
  public static WorldObject create(String id, Position pos) {
    Function<Position, WorldObject> factory = objects.get(id);
    if (factory == null) {
      throw new IllegalArgumentException("WorldObject not found: " + id);
    }
    return factory.apply(pos);
  }

  public static boolean has(String id) {
    return objects.containsKey(id);
  }

  public static String viewAll() {
    return objects.keySet().toString();
  }

}
