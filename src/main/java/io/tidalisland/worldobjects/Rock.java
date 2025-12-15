package io.tidalisland.worldobjects;

import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.items.Stone;
import io.tidalisland.utils.Position;
import java.awt.Graphics;
import java.util.List;

/**
 * Represents a rock.
 */
public class Rock extends WorldObject implements Interactable {

  private int health = 5;

  /**
   * Creates a new rock.
   */
  public Rock(Position position) {
    super("rock", position, true);
    spriteSet =
        SpriteSetBuilder.build("/sprites/worldobjects/rock.png", "/sprites/worldobjects/rock.json");
    collider = new ColliderBuilder().size(spriteSet.getFrame().getSize()).build();
    collider.updatePosition(position);
  }

  @Override
  public InteractResult interact(Player player) {
    health--;
    if (health <= 0) {
      List<Drop> drops = List.of(new Drop(new Stone(), 1, 3));
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
