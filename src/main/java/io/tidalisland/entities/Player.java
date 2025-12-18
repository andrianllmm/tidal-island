package io.tidalisland.entities;

import io.tidalisland.collision.ColliderAnchor;
import io.tidalisland.collision.ColliderBuilder;
import io.tidalisland.collision.CollisionManager;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.sprites.Sprite;
import io.tidalisland.graphics.sprites.SpriteFrame;
import io.tidalisland.graphics.sprites.SpriteSetBuilder;
import io.tidalisland.input.Action;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.inventory.Inventory;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;
import io.tidalisland.worldobjects.InteractionManager;
import java.awt.Graphics;

/**
 * The player entity.
 */
public class Player extends LivingEntity {

  private Inventory inventory;
  private Equipment equipment;

  private KeyHandler keys;

  private final long interactCooldown = 400; // milliseconds
  private long lastInteractTime = 0;
  private boolean interacting = false; // for animation

  /**
   * Creates a new player entity.
   */
  public Player(KeyHandler keys, Position position) {
    super(position, Direction.RIGHT, 4, 5.0, 0.5);

    this.inventory = new Inventory(24);
    this.equipment = new Equipment(this.inventory);

    this.keys = keys;

    // Import sprites
    spriteSet =
        SpriteSetBuilder.build("/sprites/entities/player.png", "/sprites/entities/player.json");

    // Create collider
    collider = new ColliderBuilder().size(spriteSet.getFrame().getSize()).scale(0.6)
        .anchor(ColliderAnchor.CENTER, ColliderAnchor.BOTTOM).build();
    collider.updatePosition(position);
  }

  @Override
  public void update(CollisionManager collisionManager, InteractionManager interactionManager) {
    long now = System.currentTimeMillis();

    if (keys.isJustPressed(Action.INTERACT) && now - lastInteractTime >= interactCooldown) {
      String tag = switch (direction) {
        case UP -> "interact_up";
        case DOWN -> "interact_down";
        case LEFT, RIGHT -> "interact_side";
        default -> null;
      };
      if (tag == null) {
        return;
      }

      spriteSet.setTag(tag);
      spriteSet.getCurrent().playFromStart();

      interacting = true;

      interactionManager.interact(this);
      lastInteractTime = now;
    }

    Position nextPosition = position.copy();
    if (!interacting) {
      if (keys.anyDown(Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT)) {
        if (keys.isDown(Action.UP)) {
          direction = Direction.UP;
          spriteSet.setTag("walk_up");
        } else if (keys.isDown(Action.DOWN)) {
          direction = Direction.DOWN;
          spriteSet.setTag("walk_down");
        } else if (keys.isDown(Action.LEFT)) {
          direction = Direction.LEFT;
          spriteSet.setTag("walk_side");
        } else if (keys.isDown(Action.RIGHT)) {
          direction = Direction.RIGHT;
          spriteSet.setTag("walk_side");
        }
        nextPosition.move(direction, speed);
      } else {
        switch (direction) {
          case UP -> spriteSet.setTag("idle_up");
          case DOWN -> spriteSet.setTag("idle_down");
          case LEFT, RIGHT -> spriteSet.setTag("idle_side");
          default -> {
          }
        }
      }
    }

    if (collisionManager.canMove(this, nextPosition)) {
      position.setPosition(nextPosition);
      collider.updatePosition(position);
    }

    updateHunger();

    spriteSet.update();

    Sprite current = spriteSet.getCurrent();
    if (interacting && current != null && current.isFinished()) {
      interacting = false;
    }
  }

  @Override
  public void draw(Graphics g, Camera camera) {
    if (spriteSet == null) {
      return;
    }

    Position screenPos = position.subtract(camera.getPosition());

    SpriteFrame currentFrame = spriteSet.getFrame();
    currentFrame.setFlipX(direction == Direction.LEFT);

    currentFrame.draw(g, screenPos);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public Equipment getEquipment() {
    return equipment;
  }

  public long getInteractCooldown() {
    return interactCooldown;
  }

  public boolean isInteracting() {
    return interacting;
  }
}
