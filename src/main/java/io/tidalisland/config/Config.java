package io.tidalisland.config;

/** Global configuration for the game. */
public final class Config {

  private static Config instance;

  private final ConfigData data;
  private final boolean debug;

  private Config() {
    this.data = ConfigLoader.load("/config.json");

    // Environment variables
    this.debug = Boolean.parseBoolean(System.getenv().getOrDefault("DEBUG", "false"));
  }

  /** Singleton access. */
  public static Config get() {
    if (instance == null) {
      synchronized (Config.class) {
        if (instance == null) {
          instance = new Config();
        }
      }
    }
    return instance;
  }

  public ConfigData getData() {
    return data;
  }

  public boolean getDebug() {
    return debug;
  }

  public static boolean debug() {
    return get().debug;
  }

  public static int tileSize() {
    return get().data.getTileSize();
  }

  public static int screenWidth() {
    return get().data.getScreenWidth();
  }

  public static int screenHeight() {
    return get().data.getScreenHeight();
  }

  public static int colTiles() {
    return get().data.colTiles;
  }

  public static int rowTiles() {
    return get().data.rowTiles;
  }

  public static int pixelScale() {
    return get().data.pixelScale;
  }

  public static int baseTileSize() {
    return get().data.baseTileSize;
  }

  public static int mapWidth() {
    return get().data.mapWidth;
  }

  public static int mapHeight() {
    return get().data.mapHeight;
  }

  public static int fps() {
    return get().data.fps;
  }
}
