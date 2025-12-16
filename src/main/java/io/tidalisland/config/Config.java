package io.tidalisland.config;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Random;

/**
 * Global configuration for the game.
 */
public final class Config {

  private static Config instance;

  private final ConfigData data;
  private final boolean debug;
  private final Random random;

  // Window dimensions
  private int windowWidth;
  private int windowHeight;

  // Viewport dimensions (maintains aspect ratio)
  private int viewportWidth;
  private int viewportHeight;
  private int viewportX;
  private int viewportY;

  // Scale factor (uniform)
  private double scale = 1.0;

  private Config() {
    this.data = ConfigLoader.load("/config.json");
    this.debug = Boolean.parseBoolean(System.getenv().getOrDefault("DEBUG", "false"));
    this.random = new Random();

    this.windowWidth = data.getScreenWidth();
    this.windowHeight = data.getScreenHeight();

    updateViewport();
  }

  /**
   * Gets the singleton instance of the config.
   */
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


  /**
   * Gets the native fullscreen dimensions.
   */
  public static int[] getFullscreenDimensions() {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    DisplayMode dm = gd.getDisplayMode();
    return new int[] {dm.getWidth(), dm.getHeight()};
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

  public Random getRandom() {
    return random;
  }

  public static Random random() {
    return get().random;
  }



  public static int windowWidth() {
    return get().windowWidth;
  }

  public static int windowHeight() {
    return get().windowHeight;
  }

  public static int viewportWidth() {
    return get().viewportWidth;
  }

  public static int viewportHeight() {
    return get().viewportHeight;
  }

  public static int viewportX() {
    return get().viewportX;
  }

  public static int viewportY() {
    return get().viewportY;
  }

  public static double scale() {
    return get().scale;
  }

  public static int screenWidth() {
    return get().data.getScreenWidth();
  }

  public static int screenHeight() {
    return get().data.getScreenHeight();
  }

  public static int tileSize() {
    return get().data.getTileSize();
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

  /**
   * Updates viewport to maintain aspect ratio with letterboxing.
   */
  public void updateScreenDimensions(int width, int height) {
    this.windowWidth = width;
    this.windowHeight = height;
    updateViewport();
  }

  /**
   * Calculates viewport dimensions and position to maintain aspect ratio.
   */
  private void updateViewport() {
    double targetRatio = (double) data.getScreenWidth() / data.getScreenHeight();
    double windowRatio = (double) windowWidth / windowHeight;

    if (windowRatio > targetRatio) {
      // Window is wider than target, add black bars on sides

      // Calculate scale based on height
      scale = (double) windowHeight / data.getScreenHeight();

      // Calculate viewport dimensions (rounded to prevent sub-pixel gaps)
      viewportHeight = windowHeight;
      viewportWidth = (int) Math.round(data.getScreenWidth() * scale);

      // Center horizontally
      viewportX = (windowWidth - viewportWidth) / 2;
      viewportY = 0;

    } else {
      // Window is taller than target, add black bars on top/bottom

      // Calculate scale based on width
      scale = (double) windowWidth / data.getScreenWidth();

      // Calculate viewport dimensions (rounded to prevent sub-pixel gaps)
      viewportWidth = windowWidth;
      viewportHeight = (int) Math.round(data.getScreenHeight() * scale);

      // Center vertically
      viewportX = 0;
      viewportY = (windowHeight - viewportHeight) / 2;
    }
  }
}
