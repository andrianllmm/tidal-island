package io.tidalisland.entities;

import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.utils.Position;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The player entity.
 */
public class Player extends Entity {

  public Player(Position position) {
    super(position, 4);
  }

  public Player() {
    this(new Position(SCREEN_WIDTH / 2 - (TILE_SIZE / 2), SCREEN_HEIGHT / 2 - (TILE_SIZE / 2)));
  }

  @Override
  public void update() {}

  @Override
  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight());
  }
}
