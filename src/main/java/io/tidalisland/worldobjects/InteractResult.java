package io.tidalisland.worldobjects;

import java.util.List;

/**
 * Represents the result of an interaction.
 */
public class InteractResult {
  public final List<Drop> drops;
  public final boolean destroyed;

  public InteractResult(List<Drop> drops, boolean destroyed) {
    this.drops = drops;
    this.destroyed = destroyed;
  }
}
