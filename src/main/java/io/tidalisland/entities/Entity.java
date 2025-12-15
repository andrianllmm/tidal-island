package io.tidalisland.entities;

import io.tidalisland.collision.Collider;
import io.tidalisland.collision.CollisionManager;
import io.tidalisland.config.Config;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteSet;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
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
   *
   * @param position the position
   * @param direction the direction
   * @param speed the speed
   */
  public Entity(Position position, Direction direction, int speed) {
    this.position = position;
    this.speed = speed;
    this.direction = direction;
  }

  /**
   * Creates a new entity with no direction.
   *
   * @param position the position
   * @param speed the speed
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

  /**
   * Gets the tile the entity is facing.
   *
   * @return the tile position, or null if the entity is facing none
   */
  public Position getFacingTile() {
    int tileSize = Config.tileSize();

    int col = position.getX() / tileSize;
    int row = position.getY() / tileSize;

    switch (direction) {
      case UP:
        row -= 1;
        break;
      case DOWN:
        row += 1;
        break;
      case LEFT:
        col -= 1;
        break;
      case RIGHT:
        col += 1;
        break;
      default:
        return null; // Direction.NONE
    }

    return new Position(col * tileSize, row * tileSize);
  }

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
