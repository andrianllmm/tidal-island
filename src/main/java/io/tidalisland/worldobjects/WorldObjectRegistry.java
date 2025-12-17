package io.tidalisland.worldobjects;

import io.tidalisland.utils.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Central registry for all {@link WorldObject}s. Uses {@link WorldObjectType} as the canonical key,
 * with string IDs for serialization and external data support.
 */
public final class WorldObjectRegistry {

  /** Maps WorldObjectType -> world object factory. */
  private static final Map<WorldObjectType, Function<Position, WorldObject>> objects =
      new HashMap<>();

  /** Maps string ID -> WorldObjectType for serialization/deserialization. */
  private static final Map<String, WorldObjectType> idToType = new HashMap<>();

  private WorldObjectRegistry() {}

  static {
    register(Tree.TYPE, Tree::new);
    register(Rock.TYPE, Rock::new);
    register(Bush.TYPE, Bush::new);
    register(Raft.TYPE, Raft::new);
  }

  /**
   * Registers a world object.
   *
   * @param type the canonical world object type
   * @param factory the world object factory
   * @throws IllegalArgumentException if the world object is already registered
   */
  public static void register(WorldObjectType type, Function<Position, WorldObject> factory) {
    if (objects.containsKey(type)) {
      throw new IllegalArgumentException("WorldObject already registered: " + type);
    }
    objects.put(type, factory);

    String id = type.id().toLowerCase();
    if (idToType.containsKey(id)) {
      throw new IllegalArgumentException("WorldObject ID already registered: " + type);
    }
    idToType.put(id, type);
  }

  /**
   * Creates a world object by its canonical WorldObjectType and position.
   *
   * @param type the world object type
   * @param pos the position
   * @return a new WorldObject instance
   */
  public static WorldObject create(WorldObjectType type, Position pos) {
    Function<Position, WorldObject> factory = objects.get(type);
    if (factory == null) {
      throw new IllegalArgumentException("WorldObject not found: " + type);
    }
    return factory.apply(pos);
  }

  /**
   * Creates a world object at the origin.
   *
   * @param type the world object type
   * @return the created world object
   */
  public static WorldObject create(WorldObjectType type) {
    return create(type, new Position(0, 0));
  }

  /**
   * Creates a world object by its string ID and position.
   *
   * @param id the string ID
   * @param pos the position
   * @return a new WorldObject instance
   */
  public static WorldObject create(String id, Position pos) {
    WorldObjectType type = idToType.get(id.toLowerCase());
    if (type == null) {
      throw new IllegalArgumentException("Unknown world object id: " + id);
    }
    return create(type, pos);
  }

  /**
   * Creates a world object at the origin.
   *
   * @param id the string ID
   * @return a new WorldObject instance
   */
  public static WorldObject create(String id) {
    return create(id, new Position(0, 0));
  }

  /**
   * Checks if a world object is registered by WorldObjectType.
   *
   * @param type the world object type
   * @return true if registered
   */
  public static boolean has(WorldObjectType type) {
    return objects.containsKey(type);
  }

  /**
   * Checks if a world object is registered by string ID.
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
  public static List<WorldObjectType> getAllTypes() {
    return Collections.unmodifiableList(new ArrayList<>(objects.keySet()));
  }

  /**
   * Gets all string IDs of registered world objects.
   *
   * @return unmodifiable list of IDs
   */
  public static List<String> getAllIds() {
    return Collections.unmodifiableList(new ArrayList<>(idToType.keySet()));
  }

  /**
   * Converts a string ID to its canonical WorldObjectType.
   *
   * @param id string ID
   * @return WorldObjectType
   */
  public static WorldObjectType getTypeById(String id) {
    WorldObjectType type = idToType.get(id.toLowerCase());
    if (type == null) {
      throw new IllegalArgumentException("Unknown world object id: " + id);
    }
    return type;
  }

  /**
   * Converts a WorldObjectType to its string ID.
   *
   * @param type WorldObjectType
   * @return string ID
   */
  public static String getId(WorldObjectType type) {
    return type.id();
  }
}
