package io.tidalisland.worldobjects;

import io.tidalisland.config.Config;
import io.tidalisland.utils.Position;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Loader for world objects.
 */
public class WorldObjectLoader {
  /**
   * Loads a map from a file.
   */
  public static List<WorldObject> load(String path) {
    try (InputStream is = WorldObjectLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Map file not found: " + path);
      }

      String jsonText = new String(is.readAllBytes());
      JSONObject json = new JSONObject(jsonText);

      List<WorldObject> result = new ArrayList<>();

      if (!json.has("objects")) {
        return result;
      }

      JSONArray objects = json.getJSONArray("objects");

      for (int i = 0; i < objects.length(); i++) {
        JSONObject obj = objects.getJSONObject(i);

        String id = obj.getString("id");
        JSONArray pos = obj.getJSONArray("position");

        int x = pos.getInt(0) * Config.tileSize();
        int y = pos.getInt(1) * Config.tileSize();

        WorldObject created = WorldObjectFactory.create(id, new Position(x, y));
        if (created != null) {
          result.add(created);
        } else {
          System.err.println("Unknown world object id: " + id);
        }
      }

      return result;

    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }
}
