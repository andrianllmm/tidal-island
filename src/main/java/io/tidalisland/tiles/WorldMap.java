package io.tidalisland.tiles;

import static io.tidalisland.config.Config.MAP_HEIGHT;
import static io.tidalisland.config.Config.MAP_WIDTH;
import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.graphics.Camera;
import io.tidalisland.utils.Position;
import java.awt.Graphics;

/**
 * The world map.
 */
public class WorldMap {
  Tile[][] map;

  /**
   * Initializes the world map.
   */
  public WorldMap() {
    map = new Tile[MAP_HEIGHT][MAP_WIDTH];
    map = WorldMapLoader.load("/maps/map.json");
  }

  public Tile getTile(int col, int row) {
    return map[col][row];
  }

  /**
   * Draws the world map.
   */
  public void draw(Graphics g, Camera camera) {
    for (int row = 0; row < MAP_HEIGHT; row++) {
      for (int col = 0; col < MAP_WIDTH; col++) {
        Tile tile = map[col][row];
        if (tile == null) {
          continue;
        }

        Position screenPos = new Position(col * TILE_SIZE, row * TILE_SIZE);
        screenPos = screenPos.subtract(camera.getPosition());

        // Only draw tiles that are visible on screen
        if (screenPos.getX() + TILE_SIZE < 0 || screenPos.getX() > SCREEN_WIDTH
            || screenPos.getY() + TILE_SIZE < 0 || screenPos.getY() > SCREEN_HEIGHT) {
          continue;
        }

        tile.getSprite().getFrame().draw(g, screenPos);
      }
    }
  }
}


