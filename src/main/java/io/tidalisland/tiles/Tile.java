package io.tidalisland.tiles;

import io.tidalisland.graphics.Sprite;

/**
 * A tile.
 */
public class Tile {
  private String name;
  private Sprite sprite;

  public Tile(String name, Sprite sprite) {
    this.name = name;
    this.sprite = sprite;
  }

  public String getName() {
    return name;
  }

  public Sprite getSprite() {
    return sprite;
  }
}


