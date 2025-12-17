package io.tidalisland.worldobjects;

import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.items.Apple;
import io.tidalisland.items.Leaf;
import io.tidalisland.items.Tool;
import io.tidalisland.items.Wood;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.util.List;

/**
 * Represents a tree.
 */
public class Tree extends WorldObject implements Interactable {

  private int health = 10;

  /**
   * Creates a new tree.
   */
  public Tree(Position position) {
    super("tree", position, true);
    spriteSet =
        SpriteSetBuilder.build("/sprites/worldobjects/tree.png", "/sprites/worldobjects/tree.json");
    collider = new ColliderBuilder().size(spriteSet.getFrame().getSize()).build();
    collider.updatePosition(position);
  }

  @Override
  public InteractResult interact(Player player) {
    Tool tool = player.getEquipment().getEquippedTool();
    if (tool == null) {
      health -= 1;
    } else if (tool.getType().equals("axe")) {
      health -= 3;
      tool.damage(1);
    }
    if (health <= 0) {
      List<Drop> drops =
          List.of(new Drop(new Wood(), 1), new Drop(new Leaf(), 2, 4), new Drop(new Apple(), 0, 1));
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

    spriteSet.getFrame().draw(g, screenPos);
  }
}
