package io.tidalisland.worldobjects;

import java.util.List;

/**
 * Represents the result of an interaction.
 */
public class InteractResult {
  public final List<DropDefinition> dropDefs;
  public final boolean destroyed;

  public InteractResult(List<DropDefinition> dropDefs, boolean destroyed) {
    this.dropDefs = dropDefs;
    this.destroyed = destroyed;
  }
}
