package io.tidalisland.tiles;

import io.tidalisland.graphics.Sprite;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A set of tiles.
 */
public class TileSet {

  private List<Tile> tiles = new ArrayList<>();

  public TileSet(String path) {
    load(path);
  }

  private void load(String path) {
    try (InputStream is = getClass().getResourceAsStream(path)) {
      String jsonText = new String(is.readAllBytes());
      JSONObject json = new JSONObject(jsonText);
      JSONArray arr = json.getJSONArray("tileset");

      for (int i = 0; i < arr.length(); i++) {
        JSONObject t = arr.getJSONObject(i);

        int id = t.getInt("id");
        String name = t.getString("name");
        String spritePath = t.getString("sprite");
        boolean solid = t.getBoolean("solid");

        Tile tile = new Tile(name, new Sprite(spritePath), solid);

        // Ensure the list is large enough
        while (tiles.size() <= id) {
          tiles.add(null);
        }

        tiles.set(id, tile);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Tile getTile(int id) {
    return tiles.get(id);
  }
}
