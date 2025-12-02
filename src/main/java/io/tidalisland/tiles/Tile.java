package io.tidalisland.tiles;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * A tile.
 */
public class Tile {
  private String name;
  private Sprite sprite;
  private boolean solid;

  /**
   * Initializes a tile.
   */
  public Tile(String name, Sprite sprite, boolean solid) {
    this.name = name;
    this.sprite = sprite;
    this.solid = solid;
  }

  public String getName() {
    return name;
  }

  public Sprite getSprite() {
    return sprite;
  }

  public boolean isSolid() {
    return solid;
  }

  public void setSolid(boolean solid) {
    this.solid = solid;
  }
}


