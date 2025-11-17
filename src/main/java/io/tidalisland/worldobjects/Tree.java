package io.tidalisland.worldobjects;

import static io.tidalisland.config.Config.SHOW_COLLIDERS;

import io.tidalisland.collision.ColliderFactory;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.SpriteAtlas;
import io.tidalisland.graphics.SpriteSetImporter;
import io.tidalisland.utils.Position;
import java.awt.Graphics;

/**
 * Represents a tree.
 */
public class Tree extends WorldObject {
  /**
   * Creates a new tree.
   */
  public Tree(Position position) {
    super("tree", position, true);
    spriteSet = SpriteSetImporter.fromJsonSheet(new SpriteAtlas("/sprites/worldobjects/tree.png"),
        "/sprites/worldobjects/tree.json");
    collider = ColliderFactory.create(spriteSet.getCurrentFrame().getSize());
    collider.updatePosition(position);
  }

  @Override
  public void update() {}

  @Override
  public void draw(Graphics g, Camera camera) {
    if (spriteSet == null) {
      return;
    }

    Position screenPos = position.subtract(camera.getPosition());

    spriteSet.getCurrentFrame().draw(g, screenPos);

    if (SHOW_COLLIDERS) {
      collider.draw(g, camera);
    }
  }
}
