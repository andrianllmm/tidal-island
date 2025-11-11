package io.tidalisland.entities;

import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.graphics.SpriteSet;
import io.tidalisland.utils.Dimension;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import java.awt.Graphics;

/**
 * Base class for all entities.
 */
public abstract class Entity {
  protected Position position;
  protected Dimension dimension;
  protected Direction direction = Direction.NONE;
  protected int speed;
  protected SpriteSet spriteSet;

  /**
   * Creates a new entity.
   */
  public Entity(Position position, Dimension dimension, Direction direction, int speed) {
    this.position = position;
    this.dimension = dimension;
    this.speed = speed;
    this.direction = direction;
  }

  /**
   * Creates a new entity with no direction.
   */
  public Entity(Position position, Dimension dimension, int speed) {
    this(position, dimension, Direction.NONE, speed);
  }

  /**
   * Creates a new entity with tile size as dimension.
   */
  public Entity(Position position, int speed) {
    this(position, new Dimension(TILE_SIZE, TILE_SIZE), speed);
  }

  /**
   * Updates the entity's state.
   */
  public abstract void update();

  /**
   * Draws the entity.
   */
  public abstract void draw(Graphics g);

  public Position getPosition() {
    return position;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public Direction getDirection() {
    return direction;
  }

  public int getSpeed() {
    return speed;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}

