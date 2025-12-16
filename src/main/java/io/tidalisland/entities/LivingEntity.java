package io.tidalisland.entities;

import io.tidalisland.engine.GameClock;
import io.tidalisland.items.Edible;
import io.tidalisland.utils.Direction;
import io.tidalisland.utils.Position;

/**
 * Represents an entity that has health and hunger.
 */
public abstract class LivingEntity extends Entity {

  protected int maxHealth = 100;
  protected int currentHealth = 100;

  protected int maxHunger = 100;
  protected int currentHunger = 100;

  protected final double hungerRate; // points per second
  protected final double starvationRate; // health points per second when starving

  private double hungerAccumulator = 0; // fractional hunger points
  private double starvationAccumulator = 0; // fractional damage points

  /**
   * Creates a new living entity.
   */
  public LivingEntity(Position position, Direction direction, int speed, double hungerRate,
      double starvationRate) {
    super(position, direction, speed);
    this.hungerRate = hungerRate;
    this.starvationRate = starvationRate;
  }

  /**
   * Updates hunger based on the given delta time.
   */
  public void updateHunger() {
    long deltaMillis = GameClock.getInstance().getDeltaMillis();
    double deltaSeconds = deltaMillis / 1000.0;

    // decrease hunger gradually
    if (hungerRate > 0) {
      hungerAccumulator += hungerRate * deltaSeconds;
      if (hungerAccumulator >= (int) Math.ceil(hungerRate)) {
        int decrease = (int) hungerAccumulator;
        decreaseHunger(decrease);
        hungerAccumulator -= decrease;
      }
    }

    // apply starvation damage if starving
    if (isStarving() && starvationRate > 0) {
      starvationAccumulator += starvationRate * deltaSeconds;
      if (starvationAccumulator >= (int) Math.ceil(starvationRate)) {
        int damage = (int) starvationAccumulator;
        this.damage(damage);
        starvationAccumulator -= damage;
      }
    }
  }

  /**
   * Decreases health by a given amount.
   *
   * @param amount the amount
   */
  public void damage(int amount) {
    setCurrentHealth(currentHealth - amount);
  }

  /**
   * Increases health by a given amount.
   *
   * @param amount the amount
   */
  public void heal(int amount) {
    setCurrentHealth(currentHealth + amount);
  }

  /**
   * Checks if the entity is dead.
   *
   * @return true if the entity is dead, false otherwise
   */
  public boolean isDead() {
    return currentHealth <= 0;
  }

  /**
   * Decreases hunger by a given amount.
   *
   * @param amount the amount
   */
  public void decreaseHunger(int amount) {
    setCurrentHunger(currentHunger - amount);
  }

  /**
   * Increases hunger by a given amount.
   *
   * @param amount the amount
   */
  public void increaseHunger(int amount) {
    setCurrentHunger(currentHunger + amount);
  }

  /**
   * Checks if the entity is starving.
   *
   * @return true if the entity is starving, false otherwise
   */
  public boolean isStarving() {
    return currentHunger <= 0;
  }

  /**
   * Eats a given amount of food.
   *
   * @param edible the edible item
   */
  public void eat(Edible edible) {
    if (edible.getHungerValue() > 0) {
      increaseHunger(edible.getHungerValue());
    }
  }

  public int getCurrentHealth() {
    return currentHealth;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  /** Sets the current health with a cap. */
  public void setCurrentHealth(int health) {
    currentHealth = Math.max(0, Math.min(maxHealth, health));
  }

  public int getCurrentHunger() {
    return currentHunger;
  }

  public int getMaxHunger() {
    return maxHunger;
  }

  /** Sets the current hunger with a cap. */
  public void setCurrentHunger(int hunger) {
    currentHunger = Math.max(0, Math.min(maxHunger, hunger));
  }
}
