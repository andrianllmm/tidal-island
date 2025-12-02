package io.tidalisland.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

/** Loader for configuration from JSON. */
public final class ConfigLoader {

  private ConfigLoader() {}

  /**
   * Loads configuration from JSON file.
   */
  public static ConfigData load(String path) {
    try (InputStream is = ConfigLoader.class.getResourceAsStream(path)) {
      if (is == null) {
        throw new RuntimeException(path + " not found in resources");
      }
      Map<String, Integer> map =
          new ObjectMapper().readValue(is, new TypeReference<Map<String, Integer>>() {});

      return new ConfigData(map.get("pixelScale"), map.get("baseTileSize"), map.get("colTiles"),
          map.get("rowTiles"), map.get("mapWidth"), map.get("mapHeight"), map.get("fps"));
    } catch (Exception e) {
      throw new RuntimeException("Failed to load configuration", e);
    }
  }
}
