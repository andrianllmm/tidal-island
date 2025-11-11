package io.tidalisland.tiles;

import static io.tidalisland.config.Config.COL_TILES;
import static io.tidalisland.config.Config.ROW_TILES;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.graphics.SpriteFrame;
import io.tidalisland.utils.Dimension;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    map = new Tile[COL_TILES][ROW_TILES];
    tileSet = new TileSet();

    loadMap("/maps/map00.txt");
  }

  /**
   * Loads a map from a file.
   */
  public void loadMap(String path) {
    try {
      InputStream is = getClass().getResourceAsStream(path);
      BufferedReader br = new BufferedReader(new InputStreamReader(is));

      int col = 0;
      int row = 0;

      while (col < COL_TILES && row < ROW_TILES) {
        String line = br.readLine();
        while (col < COL_TILES) {
          String[] ids = line.split(" ");
          int id = Integer.parseInt(ids[col]);
          map[col][row] = tileSet.getTile(id);
          col++;
        }
        if (col == COL_TILES) {
          col = 0;
          row++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Draws the world map.
   */
  public void draw(Graphics g) {
    int col = 0;
    int row = 0;

    while (col < COL_TILES && row < ROW_TILES) {
      Tile tile = map[col][row];

      SpriteFrame frame = tile.getSprite().getFrame();
      frame.draw(g, new Position(col * TILE_SIZE, row * TILE_SIZE),
          new Dimension(TILE_SIZE, TILE_SIZE));

      col++;
      if (col == COL_TILES) {
        col = 0;
        row++;
      }
    }
  }

}


