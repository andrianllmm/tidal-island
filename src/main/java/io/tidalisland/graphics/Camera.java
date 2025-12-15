package io.tidalisland.graphics;

import io.tidalisland.config.Config;
import io.tidalisland.entities.Entity;
import io.tidalisland.utils.Position;

/**
 * The game camera.
 */
public class Camera {
  private Position position = new Position(0, 0);

  /**
   * Updates the camera position.
   *
   * @param target the target entity to focus on
   */
  public void update(Entity target) {
    int x = target.getPosition().getX();
    int y = target.getPosition().getY();
    int w = target.getSpriteSet().getCurrentFrame().getSize().getWidth();
    int h = target.getSpriteSet().getCurrentFrame().getSize().getHeight();

    int centerX = x + (w / 2) - (Config.screenWidth() / 2);
    int centerY = y + (h / 2) - (Config.screenHeight() / 2);

    // Clamp to map boundaries
    int clampedX = Math.max(0,
        Math.min(centerX, Config.mapWidth() * Config.tileSize() - Config.screenWidth()));
    int clampedY = Math.max(0,
        Math.min(centerY, Config.mapHeight() * Config.tileSize() - Config.screenHeight()));

    position.setX(clampedX);
    position.setY(clampedY);
  }

  public Position getPosition() {
    return position;
  }
}


