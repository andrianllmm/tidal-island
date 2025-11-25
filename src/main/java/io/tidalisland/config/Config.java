package io.tidalisland.config;

import java.awt.Font;

/**
 * Configuration for the game.
 */
public class Config {
  // Tile & screen
  public static final int PIXEL_SCALE = 4;
  public static final int BASE_TILE_SIZE = 16;
  public static final int TILE_SIZE = BASE_TILE_SIZE * PIXEL_SCALE;

  public static final int COL_TILES = 16;
  public static final int ROW_TILES = 12;
  public static final int SCREEN_WIDTH = COL_TILES * TILE_SIZE;
  public static final int SCREEN_HEIGHT = ROW_TILES * TILE_SIZE;

  public static final int MAP_WIDTH = 25;
  public static final int MAP_HEIGHT = 25;

  // Game loop
  public static final int FPS = 60;

  // Global debug flags
  public static final boolean SHOW_COLLIDERS = false;

  // UI
  public static final Font UI_FONT = new Font("Press Start 2P", Font.BOLD, 18);
}

