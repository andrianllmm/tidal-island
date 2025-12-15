package io.tidalisland.tiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidalisland.graphics.sprites.Sprite;
import java.io.InputStream;
import java.util.List;

/**
 * Loader for {@link TileSet}s.
 */
public class TileSetLoader {

  /**
   * Loads a tile set from a file.
   */
  public static TileSet load(String path) {
    try (InputStream is = TileSetLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Tile set file not found: " + path);
      }

      ObjectMapper mapper = new ObjectMapper();
      TileSetData data = mapper.readValue(is, TileSetData.class);

      TileSet tileset = new TileSet();

      for (TileData t : data.tiles) {
        Tile tile = new Tile(t.id, t.name, new Sprite(t.sprite), t.solid);
        tileset.set(t.id, tile);
      }

      return tileset;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static class TileSetData {
    public List<TileData> tiles;
  }

  private static class TileData {
    public int id;
    public String name;
    public String sprite;
    public boolean solid;
  }
}
