package io.tidalisland.config;

/**
 * Flat, immutable configuration data.
 */
public final class ConfigData {

  // Tile
  public final int pixelScale;
  public final int baseTileSize;
  public final int colTiles;
  public final int rowTiles;

  // World map
  public final int mapWidth;
  public final int mapHeight;

  // Game loop
  public final int fps;

  /**
   * Creates a new config data object.
   */
  public ConfigData(int pixelScale, int baseTileSize, int colTiles, int rowTiles, int mapWidth,
      int mapHeight, int fps) {
    this.pixelScale = pixelScale;
    this.baseTileSize = baseTileSize;
    this.colTiles = colTiles;
    this.rowTiles = rowTiles;
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    this.fps = fps;
  }

  /** Derived values for convenience. */
  public int getTileSize() {
    return baseTileSize * pixelScale;
  }

  public int getScreenWidth() {
    return colTiles * getTileSize();
  }

  public int getScreenHeight() {
    return rowTiles * getTileSize();
  }
}
