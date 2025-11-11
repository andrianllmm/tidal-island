package io.tidalisland.entities;

import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.engine.KeyHandler;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The player entity.
 */
public class Player extends Entity {
  private KeyHandler keyH;

  public Player(KeyHandler keyH, Position position) {
    super(position, 4);
    this.keyH = keyH;
  }

  public Player(KeyHandler keyH) {
    this(keyH,
        new Position(SCREEN_WIDTH / 2 - (TILE_SIZE / 2), SCREEN_HEIGHT / 2 - (TILE_SIZE / 2)));
  }

  @Override
  public void update() {
    if (keyH.anyActive("up", "down", "left", "right")) {
      if (keyH.isActive("up")) {
        direction = Direction.UP;
      } else if (keyH.isActive("down")) {
        direction = Direction.DOWN;
      } else if (keyH.isActive("left")) {
        direction = Direction.LEFT;
      } else if (keyH.isActive("right")) {
        direction = Direction.RIGHT;
      }
      position.move(direction, speed);
    }
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight());
  }
}
