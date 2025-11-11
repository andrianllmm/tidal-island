package io.tidalisland.entities;

import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.SpriteSet;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;
import java.awt.Graphics;

/**
 * Base class for all entities.
 */
public abstract class Entity {
  protected Position position;
  protected Direction direction = Direction.NONE;
  protected int speed;
  protected SpriteSet spriteSet;

  /**
   * Creates a new entity.
   */
  public Entity(Position position, Direction direction, int speed) {
    this.position = position;
    this.speed = speed;
    this.direction = direction;
  }

  /**
   * Creates a new entity with no direction.
   */
  public Entity(Position position, int speed) {
    this(position, Direction.NONE, speed);
  }

  /**
   * Updates the entity's state.
   */
  public abstract void update();

  /**
   * Draws the entity.
   */
  public abstract void draw(Graphics g, Camera camera);

  public Position getPosition() {
    return position;
  }

  public Direction getDirection() {
    return direction;
  }

  public int getSpeed() {
    return speed;
  }

  public SpriteSet getSpriteSet() {
    return spriteSet;
  }

  public Size getRenderSize() {
    return spriteSet.getCurrentFrame().getSize();
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}

