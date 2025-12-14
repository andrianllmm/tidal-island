package io.tidalisland.tiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidalisland.config.Config;
import java.io.InputStream;
import java.util.List;

/**
 * Loader for world maps.
 */
public class WorldMapLoader {
  /**
   * Loads a world map from a file and returns the map data and tileset.
   */
  public static LoadResult loadWithTileSet(String path) {
    try (InputStream is = WorldMapLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Map file not found: " + path);
      }

      ObjectMapper mapper = new ObjectMapper();
      WorldMapData data = mapper.readValue(is, WorldMapData.class);

      if (data.layout.size() != Config.mapHeight()) {
        throw new IllegalArgumentException("Incorrect number of rows: " + data.layout.size());
      }

      Tile[][] map = new Tile[Config.mapHeight()][Config.mapWidth()];
      TileSet tileSet = TileSetLoader.load(data.tileset);

      for (int row = 0; row < Config.mapHeight(); row++) {
        List<Integer> tilesRow = data.layout.get(row);

        if (tilesRow.size() != Config.mapWidth()) {
          throw new IllegalArgumentException("Incorrect columns in row " + row);
        }

        for (int col = 0; col < Config.mapWidth(); col++) {
          int id = tilesRow.get(col);
          map[col][row] = tileSet.get(id);
        }
      }

      return new LoadResult(map, tileSet);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Loads a world map from a file (legacy method).
   */
  public static Tile[][] load(String path) {
    LoadResult result = loadWithTileSet(path);
    return result != null ? result.map : null;
  }

  /** Data for world map. */
  public static class WorldMapData {
    public List<List<Integer>> layout;
    public String tileset;
  }

  /** Result containing both map and tileset. */
  public static class LoadResult {
    public final Tile[][] map;
    public final TileSet tileSet;

    public LoadResult(Tile[][] map, TileSet tileSet) {
      this.map = map;
      this.tileSet = tileSet;
    }
  }
}
