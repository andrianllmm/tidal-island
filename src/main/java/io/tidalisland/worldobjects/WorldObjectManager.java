package io.tidalisland.worldobjects;

import io.tidalisland.collision.Collider;
import io.tidalisland.config.Config;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages world objects.
 */
public class WorldObjectManager {
  private final WorldMap worldMap;
  private Map<Position, WorldObject> worldObjects;

  /**
   * Creates a new world object manager.
   */
  public WorldObjectManager(WorldMap worldMap) {
    this.worldMap = worldMap;
    this.worldObjects = new HashMap<>();

    for (WorldObject obj : WorldObjectLoader.load("/worldobjects/worldobjects.json", worldMap)) {
      add(obj);
    }
  }

  /**
   * Interacts with a world object at a given position.
   */
  public void interactAt(Position pos, Player player) {
    WorldObject obj = worldObjects.get(pos);
    if (obj instanceof Interactable i) {
      i.interact(player);
    }
  }

  /**
   * Adds a world object.
   */
  public boolean add(WorldObject obj) {
    for (WorldObject existing : getAll()) {
      if (existing.getCollider().intersects(obj.getCollider())) {
        return false;
      }
    }
    worldObjects.put(obj.getPosition(), obj);
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
    Collider nextCollider = obj.getCollider().copy();
    nextCollider.updatePosition(next);

    for (WorldObject existing : getAll()) {
      if (existing != obj && existing.getCollider().intersects(nextCollider)) {
        return false;
      }
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
   * Gets a world object at a given tile position.
   */
  public WorldObject getObjectAtTile(int col, int row) {
    for (WorldObject obj : getAll()) {
      int objTileX = obj.getPosition().getX() / Config.tileSize();
      int objTileY = obj.getPosition().getY() / Config.tileSize();
      int objTileWidth = obj.getCollider().getWidth();
      int objTileHeight = obj.getCollider().getHeight();

      if (col >= objTileX && col < objTileX + objTileWidth && row >= objTileY
          && row < objTileY + objTileHeight) {
        return obj; // tile is covered by this object
      }
    }

    return null; // no object covers this tile
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
   * Number of world objects.
   */
  public int size() {
    return worldObjects.size();
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

  public WorldMap getWorldMap() {
    return worldMap;
  }
}
