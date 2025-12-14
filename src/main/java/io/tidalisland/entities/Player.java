package io.tidalisland.entities;

import io.tidalisland.collision.ColliderAnchor;
import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.collision.CollisionManager;
import io.tidalisland.config.Config;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.SpriteFrame;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.InteractionManager;
import java.awt.Graphics;

/**
 * The player entity.
 */
public class Player extends Entity {
  private Inventory inventory;
  private KeyHandler keys;

  /**
   * Creates a new player entity.
   */
  public Player(KeyHandler keys, Position position) {
    super(position, Direction.RIGHT, 4);

    this.inventory = new Inventory(24);
    this.keys = keys;

    // Import sprites
    spriteSet = SpriteSetBuilder.build(
        "/sprites/entities/player.png", "/sprites/entities/player.json");

    // Create collider
    collider = new ColliderBuilder().container(spriteSet.getCurrentFrame().getSize()).scale(0.6)
        .anchor(ColliderAnchor.CENTER, ColliderAnchor.BOTTOM).build();
    collider.updatePosition(position);
  }

  @Override
  public void update(CollisionManager collisionManager, InteractionManager interactionManager) {
    Position nextPosition = position.copy();

    if (keys.isJustPressed("interact")) {
      interactionManager.interact(this);
    }

    // Determine movement direction
    if (keys.anyDown("up", "down", "left", "right")) {
      if (keys.isDown("up")) {
        direction = Direction.UP;
        spriteSet.setTag("walk_up");
      } else if (keys.isDown("down")) {
        direction = Direction.DOWN;
        spriteSet.setTag("walk_down");
      } else if (keys.isDown("left")) {
        direction = Direction.LEFT;
        spriteSet.setTag("walk_side");
      } else if (keys.isDown("right")) {
        direction = Direction.RIGHT;
        spriteSet.setTag("walk_side");
      }
      nextPosition.move(direction, speed);
    } else {
      // Idle animation
      switch (direction) {
        case UP -> spriteSet.setTag("idle_up");
        case DOWN -> spriteSet.setTag("idle_down");
        case LEFT, RIGHT -> spriteSet.setTag("idle_side");
        default -> {
        }
      }
    }

    if (collisionManager.canMove(this, nextPosition)) {
      position.setPosition(nextPosition);
      collider.updatePosition(position);
    }

    spriteSet.update();
  }

  @Override
  public void draw(Graphics g, Camera camera) {
    if (spriteSet == null) {
      return;
    }

    Position screenPos = position.subtract(camera.getPosition());

    SpriteFrame currentFrame = spriteSet.getCurrentFrame();
    currentFrame.setFlipX(direction == Direction.LEFT);

    currentFrame.draw(g, screenPos);
  }

  public Inventory getInventory() {
    return inventory;
  }
}
