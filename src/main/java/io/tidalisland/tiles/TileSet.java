package io.tidalisland.tiles;

import java.util.ArrayList;
import java.util.List;

/**
 * A set of tiles.
 */
public class TileSet {

  private List<Tile> tiles = new ArrayList<>();

  /**
   * Gets a tile by id.
   *
   * @param id the tile id
   * @return the tile
   */
  public Tile get(int id) {
    if (id < 0 || id >= tiles.size()) {
      throw new IllegalArgumentException("Invalid tile id: " + id);
    }
    return tiles.get(id);
  }

  /**
   * Gets a tile id by name.
   *
   * @param name the tile name
   * @return the tile id
   */
  public int get(String name) {
    for (int i = 0; i < tiles.size(); i++) {
      Tile tile = tiles.get(i);
      if (tile.getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Gets all tiles.
   *
   * @return a list of tiles
   */
  public List<Tile> getAll() {
    return List.copyOf(tiles);
  }

  /**
   * Adds a tile.
   */
  public void add(Tile tile) {
    tiles.add(tile);
  }

  /**
   * Sets a tile.
   *
   * @param id the tile id
   * @param tile the tile
   */
  public void set(int id, Tile tile) {
    ensureCapacity(id);
    tiles.set(id, tile);
  }

  public int size() {
    return tiles.size();
  }

  public boolean isEmpty() {
    return tiles.isEmpty();
  }

  /** Ensures the list is large enough. */
  private void ensureCapacity(int id) {
    while (tiles.size() <= id) {
      tiles.add(null);
    }
  }
}
