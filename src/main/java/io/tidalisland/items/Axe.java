package io.tidalisland.items;

import io.tidalisland.entities.Player;
import io.tidalisland.graphics.sprites.Sprite;
import io.tidalisland.worldobjects.Tree;
import io.tidalisland.worldobjects.WorldObject;

/**
 * An axe tool.
 */
public class Axe extends Tool {

  /**
   * Creates a new axe.
   */
  public Axe() {
    super("axe", 100, 2.0);
    this.description = "A sharp axe for chopping wood.";
    this.sprite = new Sprite("/sprites/items/axe.png");
  }

  @Override
  public boolean use(WorldObject target, Player player) {
    if (!isEffectiveAgainst(target)) {
      return false;
    }

    if (isBroken()) {
      return false;
    }

    // Apply damage to the tool
    damage(1);

    return true;
  }

  @Override
  public boolean isEffectiveAgainst(WorldObject target) {
    return target instanceof Tree;
  }
}
