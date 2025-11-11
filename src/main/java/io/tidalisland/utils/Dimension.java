package io.tidalisland.utils;

import java.util.Objects;

/**
 * 2D dimension.
 */
public class Dimension {
  private int width;
  private int height;

  public Dimension(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Dimension)) {
      return false;
    }
    final Dimension other = (Dimension) obj;
    return this.width == other.getWidth() && this.height == other.getHeight();
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }

  @Override
  public String toString() {
    return width + "x" + height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
