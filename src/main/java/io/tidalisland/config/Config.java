package io.tidalisland.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

/**
 * Configuration for the game.
 */
public class Config {
  // Tile & screen
  public static final int PIXEL_SCALE;
  public static final int BASE_TILE_SIZE;
  public static final int TILE_SIZE;

  public static final int COL_TILES;
  public static final int ROW_TILES;
  public static final int SCREEN_WIDTH;
  public static final int SCREEN_HEIGHT;

  public static final int MAP_WIDTH;
  public static final int MAP_HEIGHT;

  // Game loop
  public static final int FPS;

  // Global debug flags
  public static final boolean DEBUG;

  static {
    try {
      // Load JSON from classpath
      ObjectMapper mapper = new ObjectMapper();
      InputStream is = Config.class.getResourceAsStream("/config.json");
      if (is == null) {
        throw new RuntimeException("config.json not found in resources");
      }
      ConfigData data = mapper.readValue(is, ConfigData.class);

      PIXEL_SCALE = data.tile.pixelScale;
      BASE_TILE_SIZE = data.tile.baseTileSize;
      TILE_SIZE = BASE_TILE_SIZE * PIXEL_SCALE;
      COL_TILES = data.tile.colTiles;
      ROW_TILES = data.tile.rowTiles;
      SCREEN_WIDTH = COL_TILES * TILE_SIZE;
      SCREEN_HEIGHT = ROW_TILES * TILE_SIZE;
      MAP_WIDTH = data.worldMap.width;
      MAP_HEIGHT = data.worldMap.height;
      FPS = data.fps;

      // Environment variables
      Map<String, String> env = System.getenv();
      DEBUG = env.containsKey("DEBUG") ? Boolean.parseBoolean(env.get("DEBUG")) : false;

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to load configuration", e);
    }
  }

  /** Configuration data. */
  public static class ConfigData {
    public TileConfig tile;
    public WorldMapConfig worldMap;
    public int fps = 60;
  }

  /** Tile configuration. */
  public static class TileConfig {
    public int pixelScale;
    public int baseTileSize;
    public int colTiles;
    public int rowTiles;
  }

  /** World map configuration. */
  public static class WorldMapConfig {
    public int width;
    public int height;
  }
}
