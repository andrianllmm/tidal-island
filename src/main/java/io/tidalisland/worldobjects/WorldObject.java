package io.tidalisland.worldobjects;

import io.tidalisland.collision.Collider;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.SpriteSet;
import io.tidalisland.utils.Position;
import java.awt.Graphics;

/**
 * Represents an object in the world.
 */
public abstract class WorldObject {
  protected String name;
  protected Position position;
  protected SpriteSet spriteSet;
  protected Collider collider;
  protected boolean solid;

  /**
   * Creates a new world object.
   */
  public WorldObject(String name, Position position, boolean solid) {
    this.name = name;
    this.position = position;
    this.solid = solid;
  }

  public void interact(Player player) {}

  public abstract void update();

  public abstract void draw(Graphics g, Camera camera);

  public String getName() {
    return name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public SpriteSet getSpriteSet() {
    return spriteSet;
  }

  public Collider getCollider() {
    return collider;
  }

  public boolean isSolid() {
    return solid;
  }

  public void setSolid(boolean solid) {
    this.solid = solid;
  }
}
