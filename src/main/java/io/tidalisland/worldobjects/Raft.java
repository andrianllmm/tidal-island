package io.tidalisland.worldobjects;

import io.tidalisland.collision.ColliderAnchor;
import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.config.Config;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.tiles.Tile;
import io.tidalisland.tiles.WorldMap;
import io.tidalisland.utils.Position;
import java.awt.Graphics;

/**
 * Represents a raft.
 */
public class Raft extends WorldObject {
  private WorldMap worldMap;

  /**
   * Creates a new raft.
   */
  public Raft(Position position) {
    super("raft", position, false);
    setFloatable(true);
    spriteSet = SpriteSetBuilder.build(
        "/sprites/worldobjects/raft.png", "/sprites/worldobjects/raft.json");
    spriteSet.setTag("floats");
    collider = new ColliderBuilder().container(spriteSet.getCurrentFrame().getSize())
        .scale(1, 0.8).anchor(ColliderAnchor.CENTER, ColliderAnchor.TOP).build();
    collider.updatePosition(position);
  }

  public void setWorldMap(WorldMap worldMap) {
    this.worldMap = worldMap;
  }

  @Override
  public void update() {
    if (spriteSet == null || worldMap == null) {
      return;
    }

    // Determine the top-left tile the raft occupies
    int tileX = position.getX() / Config.tileSize();
    int tileY = position.getY() / Config.tileSize();

    Tile tile = worldMap.getTile(tileX, tileY);
    if (tile != null && tile.getName().equals("water")) {
      spriteSet.setTag("floats");
    } else {
      spriteSet.setTag("idle");
    }

    collider.updatePosition(position);

    spriteSet.update();
  }

  @Override
  public void draw(Graphics g, Camera camera) {
    if (spriteSet == null) {
      return;
    }

    Position screenPos = position.subtract(camera.getPosition());

    spriteSet.getCurrentFrame().draw(g, screenPos);
  }
}
