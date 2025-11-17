package io.tidalisland.worldobjects;

import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.graphics.Camera;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages world objects.
 */
public class WorldObjectManager {
  Map<Position, WorldObject> worldObjects;

  public WorldObjectManager() {
    worldObjects = new HashMap<>();
    init();
  }

  /**
   * Initializes the world objects.
   */
  private void init() {
    add(new Tree(new Position(10 * TILE_SIZE, 10 * TILE_SIZE)));
    add(new Tree(new Position(9 * TILE_SIZE, 8 * TILE_SIZE)));
    add(new Tree(new Position(6 * TILE_SIZE, 7 * TILE_SIZE)));
    add(new Tree(new Position(11 * TILE_SIZE, 13 * TILE_SIZE)));
    add(new Tree(new Position(5 * TILE_SIZE, 14 * TILE_SIZE)));
  }

  /**
   * Adds a world object.
   */
  public boolean add(WorldObject obj) {
    Position pos = obj.getPosition();
    if (worldObjects.containsKey(pos)) {
      return false;
    }
    worldObjects.put(pos, obj);
    return true;
  }

  public void replace(WorldObject obj) {
    Position pos = obj.getPosition();
    worldObjects.put(pos, obj);
  }

  /**
   * Removes a world object.
   */
  public void remove(WorldObject obj) {
    remove(obj.getPosition());
  }

  /**
   * Removes a world object at a given position.
   */
  public void remove(Position pos) {
    worldObjects.remove(pos);
  }

  /**
   * Moves a world object from one position to another.
   */
  public boolean move(WorldObject obj, Position next) {
    if (worldObjects.containsKey(next)) {
      return false;
    }
    worldObjects.remove(obj.getPosition());
    obj.setPosition(next);
    worldObjects.put(next, obj);
    return true;
  }


  /**
   * Moves a world object at a given position to another.
   */
  public void move(Position pos, Position next) {
    move(get(pos), next);
  }

  /**
   * Gets a world object at a given position.
   */
  public WorldObject get(Position pos) {
    return worldObjects.get(pos);
  }

  /**
   * Gets all world objects.
   */
  public Collection<WorldObject> getAll() {
    return worldObjects.values();
  }

  /**
   * Has a world object at a given position.
   */
  public boolean has(Position pos) {
    return worldObjects.containsKey(pos);
  }


  /**
   * Has a world object.
   */
  public boolean has(WorldObject obj) {
    return worldObjects.containsValue(obj);
  }

  /**
   * Updates the world objects.
   */
  public void update() {
    for (WorldObject worldObject : getAll()) {
      worldObject.update();
    }
  }

  /**
   * Draws the world objects.
   */
  public void draw(Graphics g, Camera camera) {
    for (WorldObject worldObject : getAll()) {
      worldObject.draw(g, camera);
    }
  }
}
