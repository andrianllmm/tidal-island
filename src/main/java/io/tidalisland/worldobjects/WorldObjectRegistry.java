package io.tidalisland.worldobjects;

import io.tidalisland.utils.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Central registry for all {@link WorldObject}s.
 */
public final class WorldObjectRegistry {

  private static final Map<String, Function<Position, WorldObject>> objects = new HashMap<>();

  private WorldObjectRegistry() {}

  static {
    register("tree", Tree::new);
    register("rock", Rock::new);
    register("bush", Bush::new);
    register("raft", Raft::new);
  }

  /**
   * Registers a world object.
   *
   * @param id the world object id
   * @param factory the world object factory
   * @throws IllegalArgumentException if the world object is already registered
   */
  public static void register(String id, Function<Position, WorldObject> factory) {
    if (objects.containsKey(id)) {
      throw new IllegalArgumentException("WorldObject already registered: " + id);
    }
    objects.put(id, factory);
  }

  /**
   * Creates a world object at the given position.
   *
   * @param id the world object id
   * @param pos the position
   * @return the created world object
   */
  public static WorldObject create(String id, Position pos) {
    Function<Position, WorldObject> factory = objects.get(id);
    if (factory == null) {
      throw new IllegalArgumentException("WorldObject not found: " + id);
    }
    return factory.apply(pos);
  }

  /**
   * Creates a world object at the origin.
   *
   * @param id the world object id
   * @return the created world object
   */
  public static WorldObject create(String id) {
    return create(id, new Position(0, 0));
  }

  /**
   * Gets all world object ids.
   *
   * @return a list of world object ids
   */
  public static List<String> getAllIds() {
    return new ArrayList<>(objects.keySet());
  }

  /**
   * Checks if a world object is registered.
   *
   * @param id the world object id
   * @return true if the world object is registered, false otherwise
   */
  public static boolean has(String id) {
    return objects.containsKey(id);
  }
}
