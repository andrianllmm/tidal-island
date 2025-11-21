package io.tidalisland.worldobjects;

import io.tidalisland.utils.Position;

/**
 * Creates world objects.
 */
public class WorldObjectFactory {
  /**
   * Creates a world object.
   */
  public static WorldObject create(String id, Position pos) {
    return switch (id) {
      case "tree" -> new Tree(pos);
      default -> null;
    };
  }
}
