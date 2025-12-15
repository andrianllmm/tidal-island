package io.tidalisland.worldobjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidalisland.config.Config;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader for {@link WorldObject}s.
 */
public class WorldObjectLoader {

  /**
   * Loads world objects from a file.
   */
  public static List<WorldObject> load(String path, WorldMap worldMap) {
    try (InputStream is = WorldObjectLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new IllegalArgumentException("Map file not found: " + path);
      }

      ObjectMapper mapper = new ObjectMapper();
      WorldObjectData data = mapper.readValue(is, WorldObjectData.class);

      if (data.objects == null) {
        return List.of();
      }

      List<WorldObject> result = new ArrayList<>();

      for (ObjectEntry obj : data.objects) {
        int x = obj.position.get(0) * Config.tileSize();
        int y = obj.position.get(1) * Config.tileSize();

        WorldObject created = WorldObjectRegistry.create(obj.id, new Position(x, y));
        if (created instanceof Raft raft) {
          raft.setWorldMap(worldMap);
        }
        if (created != null) {
          result.add(created);
        } else {
          System.err.println("Unknown world object id: " + obj.id);
        }
      }

      return result;

    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }

  /** Data for world objects. */
  public static class WorldObjectData {
    public List<ObjectEntry> objects;
  }

  /** Data for a single world object. */
  public static class ObjectEntry {
    public String id;

    public List<Integer> position;
  }
}
