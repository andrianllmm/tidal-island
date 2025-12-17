package io.tidalisland.worldobjects;

import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.items.Leaf;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.util.List;

/**
 * Represents a bush.
 */
public class Bush extends WorldObject implements Interactable {

  public static final WorldObjectType TYPE = new WorldObjectType("bush");

  private int health = 2;

  /**
   * Creates a new bush.
   */
  public Bush(Position position) {
    super(TYPE, position, true);
    spriteSet =
        SpriteSetBuilder.build("/sprites/worldobjects/bush.png", "/sprites/worldobjects/bush.json");
    collider = new ColliderBuilder().size(spriteSet.getFrame().getSize()).scale(0.8).build();
    collider.updatePosition(position);
  }

  @Override
  public InteractResult interact(Player player) {
    health--;
    if (health <= 0) {
      List<Drop> drops = List.of(new Drop(new Leaf(), 3, 5));
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
