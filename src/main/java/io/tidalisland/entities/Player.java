package io.tidalisland.entities;

import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;
import static io.tidalisland.config.Config.SHOW_COLLIDERS;
import static io.tidalisland.config.Config.TILE_SIZE;

import io.tidalisland.collision.ColliderAnchor;
import io.tidalisland.collision.ColliderFactory;
import io.tidalisland.collision.CollisionManager;
import io.tidalisland.engine.KeyHandler;
import io.tidalisland.graphics.Camera;
import io.tidalisland.graphics.SpriteAtlas;
import io.tidalisland.graphics.SpriteFrame;
import io.tidalisland.graphics.SpriteSetImporter;
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
  private KeyHandler keyH;

  /**
   * Creates a new player entity.
   */
  public Player(KeyHandler keyH, Position position) {
    super(position, 4);

    this.inventory = new Inventory();
    this.keyH = keyH;

    // Import sprites
    spriteSet = SpriteSetImporter.fromJsonSheet(new SpriteAtlas("/sprites/entities/player.png"),
        "/sprites/entities/player.json");

    // Create collider
    collider = ColliderFactory.create(spriteSet.getCurrentFrame().getSize(), 0.6, 0.6,
        ColliderAnchor.CENTER, ColliderAnchor.BOTTOM);
    collider.updatePosition(position);
  }

  public Player(KeyHandler keyH) {
    this(keyH,
        new Position(SCREEN_WIDTH / 2 - (TILE_SIZE / 2), SCREEN_HEIGHT / 2 - (TILE_SIZE / 2)));
  }

  @Override
  public void update(CollisionManager collisionManager, InteractionManager interactionManager) {
    Position nextPosition = position.copy();

    if (keyH.isJustPressed("interact")) {
      interactionManager.interact(this);
    }

    // Determine movement direction
    if (keyH.anyDown("up", "down", "left", "right")) {
      if (keyH.isDown("up")) {
        direction = Direction.UP;
        spriteSet.setTag("walk_up");
      } else if (keyH.isDown("down")) {
        direction = Direction.DOWN;
        spriteSet.setTag("walk_down");
      } else if (keyH.isDown("left")) {
        direction = Direction.LEFT;
        spriteSet.setTag("walk_side");
      } else if (keyH.isDown("right")) {
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

    if (SHOW_COLLIDERS) {
      collider.draw(g, camera);
    }
  }

  public Inventory getInventory() {
    return inventory;
  }
}
