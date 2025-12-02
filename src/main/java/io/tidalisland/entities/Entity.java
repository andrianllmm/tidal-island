package io.tidalisland.entities;

import io.tidalisland.collision.Collider;
import io.tidalisland.collision.CollisionManager;
import io.tidalisland.config.Config;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.SpriteSet;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;
import io.tidalisland.worldobjects.InteractionManager;
import java.awt.Graphics;

/**
 * Base class for all entities.
 */
public abstract class Entity {
  protected Position position;
  protected Direction direction = Direction.NONE;
  protected Collider collider;
  protected SpriteSet spriteSet;
  protected int speed;
  protected int interactionRange = Config.tileSize() / 4;

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
  public abstract void update(CollisionManager collisionManager,
      InteractionManager interactionManager);

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

  public SpriteSet getSpriteSet() {
    return spriteSet;
  }

  public Collider getCollider() {
    return collider;
  }

  public int getSpeed() {
    return speed;
  }

  public Size getRenderSize() {
    return spriteSet.getCurrentFrame().getSize();
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public int getInteractionRange() {
    return interactionRange;
  }

  public void setInteractionRange(int interactionRange) {
    this.interactionRange = interactionRange;
  }
}

