package io.tidalisland.items;

/**
 * Base class for food items.
 */
public abstract class Food extends Item implements Edible {

  private final int hungerValue;

  public Food(ItemType type, int maxStackSize, int hungerValue) {
    super(type, maxStackSize);
    this.hungerValue = hungerValue;
  }

  @Override
  public int getHungerValue() {
    return hungerValue;
  }
}
