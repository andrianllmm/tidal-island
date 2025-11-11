package io.tidalisland.config;

/**
 * Configuration for the game.
 */
public class Config {
  // Tile & screen
  public static final int BASE_TILE_SIZE = 32;
  public static final int TILE_SCALE = 2;
  public static final int TILE_SIZE = BASE_TILE_SIZE * TILE_SCALE;

  public static final int COL_TILES = 16;
  public static final int ROW_TILES = 12;
  public static final int SCREEN_WIDTH = COL_TILES * TILE_SIZE;
  public static final int SCREEN_HEIGHT = ROW_TILES * TILE_SIZE;

  public static final int MAP_WIDTH = 25;
  public static final int MAP_HEIGHT = 25;

  // Game loop
  public static final int FPS = 60;
}

