package io.tidalisland.graphics;

import static io.tidalisland.config.Config.MAP_HEIGHT;
import static io.tidalisland.config.Config.MAP_WIDTH;
import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.entities.Entity;
import io.tidalisland.utils.Position;

/**
 * The game camera.
 */
public class Camera {
  private Position position = new Position(0, 0);

  /**
   * Updates the camera position.
   */
  public void update(Entity target) {
    int x = target.getPosition().getX();
    int y = target.getPosition().getY();
    int w = target.getSpriteSet().getCurrent().getFrame().getSize().getWidth();
    int h = target.getSpriteSet().getCurrent().getFrame().getSize().getHeight();

    int centerX = x + (w / 2) - (SCREEN_WIDTH / 2);
    int centerY = y + (h / 2) - (SCREEN_HEIGHT / 2);

    // Clamp to map boundaries
    int clampedX = Math.max(0, Math.min(centerX, MAP_WIDTH * TILE_SIZE - SCREEN_WIDTH));
    int clampedY = Math.max(0, Math.min(centerY, MAP_HEIGHT * TILE_SIZE - SCREEN_HEIGHT));

    position.setX(clampedX);
    position.setY(clampedY);
  }

  public Position getPosition() {
    return position;
  }
}


