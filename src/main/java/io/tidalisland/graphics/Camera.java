package io.tidalisland.graphics;

import io.tidalisland.config.Config;
import io.tidalisland.entities.Entity;
import io.tidalisland.utils.Position;

/**
 * The game camera.
 */
public class Camera {

  private Position position = new Position(0, 0);
  private int speed = 1;

  /**
   * Updates the camera to follow a target entity.
   *
   * @param target the target entity to focus on
   */
  public void update(Entity target) {
    if (target == null) {
      return; // do nothing if no target
    }

    int x = target.getPosition().getX();
    int y = target.getPosition().getY();
    int w = target.getCollider().getWidth();
    int h = target.getCollider().getHeight();

    int centerX = x + (w / 2) - (Config.screenWidth() / 2);
    int centerY = y + (h / 2) - (Config.screenHeight() / 2);

    int clampedX = getClampedX(centerX);
    int clampedY = getClampedY(centerY);

    position.setX(clampedX);
    position.setY(clampedY);
  }

  /**
   * Updates the camera manually for free movement.
   *
   * @param dx change in x
   * @param dy change in y
   */
  public void update(int dx, int dy) {
    int newX = position.getX() + dx * speed;
    int newY = position.getY() + dy * speed;

    newX = getClampedX(newX);
    newY = getClampedY(newY);

    position.setX(newX);
    position.setY(newY);
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position.setPosition(position);
  }

  public void setPosition(int x, int y) {
    position.setX(x);
    position.setY(y);
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  private int getClampedX(int x) {
    return Math.max(0, Math.min(x, Config.mapWidth() * Config.tileSize() - Config.screenWidth()));
  }

  private int getClampedY(int y) {
    return Math.max(0, Math.min(y, Config.mapHeight() * Config.tileSize() - Config.screenHeight()));
  }
}
