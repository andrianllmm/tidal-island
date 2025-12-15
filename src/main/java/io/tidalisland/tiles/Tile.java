package io.tidalisland.tiles;

import io.tidalisland.graphics.sprites.Sprite;

/**
 * A tile.
 */
public class Tile {

  private final int id;
  private final String name;
  private Sprite sprite;
  private boolean solid;

  /**
   * Initializes a tile.
   *
   * @param id the tile id
   * @param name the tile name
   * @param sprite the tile sprite
   * @param solid whether the tile is solid
   */
  public Tile(int id, String name, Sprite sprite, boolean solid) {
    this.id = id;
    this.name = name;
    this.sprite = sprite;
    this.solid = solid;
  }

  public int getId() {
    return id;
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


