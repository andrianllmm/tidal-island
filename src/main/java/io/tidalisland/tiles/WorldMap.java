package io.tidalisland.tiles;

import io.tidalisland.config.Config;
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
    map = new Tile[Config.mapHeight()][Config.mapWidth()];
    map = WorldMapLoader.load("/maps/map.json");
  }

  public Tile getTile(int col, int row) {
    return map[col][row];
  }

  /**
   * Draws the world map.
   */
  public void draw(Graphics g, Camera camera) {
    for (int row = 0; row < Config.mapHeight(); row++) {
      for (int col = 0; col < Config.mapWidth(); col++) {
        Tile tile = map[col][row];
        if (tile == null) {
          continue;
        }

        Position screenPos = new Position(col * Config.tileSize(), row * Config.tileSize());
        screenPos = screenPos.subtract(camera.getPosition());

        // Only draw tiles that are visible on screen
        if (screenPos.getX() + Config.tileSize() < 0 || screenPos.getX() > Config.screenWidth()
            || screenPos.getY() + Config.tileSize() < 0
            || screenPos.getY() > Config.screenHeight()) {
          continue;
        }

        tile.getSprite().getFrame().draw(g, screenPos);
      }
    }
  }
}


