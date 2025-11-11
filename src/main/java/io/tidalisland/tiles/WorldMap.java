package io.tidalisland.tiles;

import static io.tidalisland.config.Config.MAP_HEIGHT;
import static io.tidalisland.config.Config.MAP_WIDTH;
import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.graphics.Camera;
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
    map = new Tile[MAP_HEIGHT][MAP_WIDTH];
    tileSet = new TileSet();

    loadMap("/maps/map00.txt");
  }

  /**
   * Loads a map from a file.
   */
  public void loadMap(String path) {
    try (InputStream is = getClass().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

      String line;
      int row = 0;

      while ((line = br.readLine()) != null) {
        if (row >= MAP_HEIGHT) {
          throw new IllegalArgumentException("Map file has more rows than expected: " + MAP_HEIGHT);
        }

        String[] ids = line.trim().split("\\s+");
        if (ids.length != MAP_WIDTH) {
          throw new IllegalArgumentException("Map file row " + row
              + " has incorrect number of columns: " + ids.length + ", expected: " + MAP_WIDTH);
        }

        for (int col = 0; col < MAP_WIDTH; col++) {
          int id = Integer.parseInt(ids[col]);
          map[col][row] = tileSet.getTile(id);
        }

        row++;
      }

      if (row != MAP_HEIGHT) {
        throw new IllegalArgumentException(
            "Map file has incorrect number of rows: " + row + ", expected: " + MAP_HEIGHT);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
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


