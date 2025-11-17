package io.tidalisland.tiles;

import static io.tidalisland.config.Config.MAP_HEIGHT;
import static io.tidalisland.config.Config.MAP_WIDTH;

import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Loader for world maps.
 */
public class WorldMapLoader {
  /**
   * Loads a world map from a file.
   */
  public static Tile[][] load(String path) {
    try (InputStream is = WorldMapLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Map file not found: " + path);
      }

      String jsonText = new String(is.readAllBytes());
      JSONObject json = new JSONObject(jsonText);
      JSONArray tiles = json.getJSONArray("tiles");
      String tilesetPath = json.getString("tileset");

      if (tiles.length() != MAP_HEIGHT) {
        throw new IllegalArgumentException("Incorrect number of rows: " + tiles.length());
      }

      Tile[][] map = new Tile[MAP_HEIGHT][MAP_WIDTH];
      TileSet tileSet = new TileSet(tilesetPath);

      for (int row = 0; row < MAP_HEIGHT; row++) {
        JSONArray tilesRow = tiles.getJSONArray(row);

        if (tilesRow.length() != MAP_WIDTH) {
          throw new IllegalArgumentException("Incorrect columns in row " + row);
        }

        for (int col = 0; col < MAP_WIDTH; col++) {
          int id = tilesRow.getInt(col);
          map[col][row] = tileSet.getTile(id);
        }
      }

      return map;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
