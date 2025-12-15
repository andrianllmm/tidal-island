package io.tidalisland.utils;

import java.util.Objects;

/**
 * Represents a size in a 2D space.
 */
public class Size {

  private int width;
  private int height;

  /**
   * Creates a new Size with the specified width and height.
   *
   * @param width the width
   * @param height the height
   */
  public Size(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Scales this size by the specified factors. Values are rounded to the nearest integer.
   *
   * @param scaleX factor to scale width
   * @param scaleY factor to scale height
   */
  public void scale(double scaleX, double scaleY) {
    width = (int) (width * scaleX);
    height = (int) (height * scaleY);
  }

  /**
   * Creates a copy of this size.
   *
   * @return a new Size with the same width and height
   */
  public Size copy() {
    return new Size(width, height);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Size)) {
      return false;
    }
    Size other = (Size) obj;
    return width == other.getWidth() && height == other.getHeight();
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

  /**
   * Updates this size to match another size.
   *
   * @param size the size to copy from
   */
  public void setSize(Size size) {
    setWidth(size.getWidth());
    setHeight(size.getHeight());
  }
}
