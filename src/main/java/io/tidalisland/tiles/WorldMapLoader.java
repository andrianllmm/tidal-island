package io.tidalisland.tiles;

import io.tidalisland.config.Config;
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

      if (tiles.length() != Config.mapHeight()) {
        throw new IllegalArgumentException("Incorrect number of rows: " + tiles.length());
      }

      Tile[][] map = new Tile[Config.mapHeight()][Config.mapWidth()];
      TileSet tileSet = new TileSet(tilesetPath);

      for (int row = 0; row < Config.mapHeight(); row++) {
        JSONArray tilesRow = tiles.getJSONArray(row);

        if (tilesRow.length() != Config.mapWidth()) {
          throw new IllegalArgumentException("Incorrect columns in row " + row);
        }

        for (int col = 0; col < Config.mapWidth(); col++) {
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
