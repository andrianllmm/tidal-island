package io.tidalisland.worldobjects;

/**
 * Type of a world object.
 */
public class WorldObjectType {

  private final String id;

  public WorldObjectType(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WorldObjectType other)) {
      return false;
    }
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return id;
  }
}
