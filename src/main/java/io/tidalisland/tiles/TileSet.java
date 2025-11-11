package io.tidalisland.tiles;

import io.tidalisland.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

/**
 * A set of tiles.
 */
public class TileSet {
  List<Tile> tiles = new ArrayList<>();

  public TileSet() {
    loadTiles();
  }

  public Tile getTile(int id) {
    return tiles.get(id);
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  /**
   * Loads the tiles with their sprites and names.
   */
  private void loadTiles() {
    Tile grass = new Tile("dirt", new Sprite("/sprites/tiles/grass.png"));
    Tile sand = new Tile("sand", new Sprite("/sprites/tiles/sand.png"));
    Tile water = new Tile("water", new Sprite("/sprites/tiles/water.png"));

    tiles.add(grass);
    tiles.add(sand);
    tiles.add(water);
  }
}

