package io.tidalisland.worldobjects;

import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.util.List;

/**
 * Represents a tree.
 */
public class Tree extends WorldObject implements Interactable {
  private int health = 5;

  /**
   * Creates a new tree.
   */
  public Tree(Position position) {
    super("tree", position, true);
    spriteSet =
        SpriteSetBuilder.build("/sprites/worldobjects/tree.png", "/sprites/worldobjects/tree.json");
    collider = new ColliderBuilder().container(spriteSet.getCurrentFrame().getSize()).build();
    collider.updatePosition(position);
  }

  @Override
  public InteractResult interact(Player player) {
    health--;
    if (health <= 0) {
      List<DropDefinition> drops =
          List.of(new DropDefinition("wood", 1), new DropDefinition("leaf", 2, 4));
      return new InteractResult(drops, true);
    }
    return new InteractResult(List.of(), false);
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
  }
}
