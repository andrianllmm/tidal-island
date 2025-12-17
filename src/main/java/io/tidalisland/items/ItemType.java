package io.tidalisland.items;

import java.util.Objects;

/**
 * Type of an item.
 */
public final class ItemType {

  private final String id;

  public ItemType(String id) {
    this.id = Objects.requireNonNull(id);
  }

  public String id() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ItemType other)) {
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
