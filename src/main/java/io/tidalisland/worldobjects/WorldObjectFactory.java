package io.tidalisland.worldobjects;

import io.tidalisland.utils.Position;

/**
 * Creates world objects.
 */
public class WorldObjectFactory {
  /**
   * Creates a world object.
   */
  public static WorldObject create(String type, Position pos) {
    return switch (type) {
      case "tree" -> new Tree(pos);
      default -> null;
    };
  }
}
