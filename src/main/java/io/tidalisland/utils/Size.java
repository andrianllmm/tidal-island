package io.tidalisland.utils;

import java.util.Objects;

/**
 * 2D size.
 */
public class Size {
  private int width;
  private int height;

  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void scale(double scaleX, double scaleY) {
    width = (int) (width * scaleX);
    height = (int) (height * scaleY);
  }

  public Size copy() {
    return new Size(width, height);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Size)) {
      return false;
    }
    final Size other = (Size) obj;
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
