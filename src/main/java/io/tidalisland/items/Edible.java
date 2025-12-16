package io.tidalisland.items;

/**
 * Marks something as edible.
 */
public interface Edible {
  /** Returns how much hunger this item restores. */
  int getHungerValue();
}
