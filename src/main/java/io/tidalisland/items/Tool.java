package io.tidalisland.items;

import io.tidalisland.entities.Player;
import io.tidalisland.worldobjects.WorldObject;

/**
 * Base class for all tools that can be equipped and used.
 */
public abstract class Tool extends Item {

  protected final int durability;
  protected int currentDurability;
  protected final double damageMultiplier;

  /**
   * Creates a new tool.
   *
   * @param type the item type
   * @param durability max durability
   * @param damageMultiplier damage multiplier for this tool
   */
  public Tool(String type, int durability, double damageMultiplier) {
    super(type, 1); // tools are not stackable
    this.durability = durability;
    this.currentDurability = durability;
    this.damageMultiplier = damageMultiplier;
  }

  /**
   * Uses the tool on a world object.
   *
   * @param target the target object
   * @param player the player using the tool
   * @return true if the tool was used successfully
   */
  public abstract boolean use(WorldObject target, Player player);

  /**
   * Checks if this tool is effective against a target.
   *
   * @param target the target object
   * @return true if the tool is effective
   */
  public abstract boolean isEffectiveAgainst(WorldObject target);

  /**
   * Damages the tool by a given amount.
   *
   * @param amount the amount of damage
   */
  public void damage(int amount) {
    currentDurability = Math.max(0, currentDurability - amount);
  }

  /**
   * Checks if the tool is broken.
   *
   * @return true if durability is 0
   */
  public boolean isBroken() {
    return currentDurability <= 0;
  }

  /**
   * Repairs the tool.
   *
   * @param amount the amount to repair
   */
  public void repair(int amount) {
    currentDurability = Math.min(durability, currentDurability + amount);
  }

  /**
   * Gets the durability percentage.
   *
   * @return durability as a percentage (0.0 to 1.0)
   */
  public double getDurabilityPercent() {
    return (double) currentDurability / durability;
  }

  public int getDurability() {
    return durability;
  }

  public int getCurrentDurability() {
    return currentDurability;
  }

  public double getDamageMultiplier() {
    return damageMultiplier;
  }
}
