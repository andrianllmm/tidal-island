package io.tidalisland.items;

/**
 * Base class for food items.
 */
public class Food extends Item implements Edible {

  private final int hungerValue;

  public Food(String type, int maxStackSize, int hungerValue) {
    super(type, maxStackSize);
    this.hungerValue = hungerValue;
  }

  @Override
  public int getHungerValue() {
    return hungerValue;
  }
}
