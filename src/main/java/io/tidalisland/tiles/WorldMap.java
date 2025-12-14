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
  TileSet tileSet;

  /**
   * Initializes the world map.
   */
  public WorldMap() {
    WorldMapLoader.LoadResult result = WorldMapLoader.loadWithTileSet("/maps/map.json");
    if (result != null) {
      this.map = result.map;
      this.tileSet = result.tileSet;
    } else {
      this.map = new Tile[Config.mapHeight()][Config.mapWidth()];
    }
  }

  /**
   * Gets the tile at the given position.
   */
  public Tile getTile(int col, int row) {
    if (col < 0 || col >= Config.mapWidth() || row < 0 || row >= Config.mapHeight()) {
      return null;
    }
    return map[col][row];
  }

  /**
   * Sets a tile at the given position.
   */
  public void setTile(int col, int row, int tileId) {
    if (col < 0 || col >= Config.mapWidth() || row < 0 || row >= Config.mapHeight()) {
      return;
    }
    if (tileSet == null) {
      System.err.println("Warning: TileSet not initialized in WorldMap");
      return;
    }
    map[col][row] = tileSet.get(tileId);
  }

  /**
   * Sets the tile set for this world map.
   */
  public void setTileSet(TileSet tileSet) {
    this.tileSet = tileSet;
  }

  public TileSet getTileSet() {
    return tileSet;
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
