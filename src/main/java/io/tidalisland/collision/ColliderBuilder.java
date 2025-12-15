package io.tidalisland.collision;

import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;

/**
 * Builder for creating {@link Collider}s.
 */
public class ColliderBuilder {
  private Size size;
  private double scaleX = 1.0;
  private double scaleY = 1.0;
  private ColliderAnchor anchorX = ColliderAnchor.CENTER;
  private ColliderAnchor anchorY = ColliderAnchor.CENTER;

  /**
   * Sets the size which the collider will be based on.
   *
   * @param size the size
   * @return this builder
   */
  public ColliderBuilder size(Size size) {
    this.size = size;
    return this;
  }

  /**
   * Sets the scaling factors for X and Y axes.
   *
   * @param scaleX the scale factor for the X axis
   * @param scaleY the scale factor for the Y axis
   * @return this builder
   */
  public ColliderBuilder scale(double scaleX, double scaleY) {
    this.scaleX = scaleX;
    this.scaleY = scaleY;
    return this;
  }

  /**
   * Sets uniform scaling for both axes.
   *
   * @param scale the scale factor
   * @return this builder
   * @see #scale(double, double)
   */
  public ColliderBuilder scale(double scale) {
    return scale(scale, scale);
  }

  /**
   * Sets anchor points for both X and Y axes.
   *
   * @param anchorX the anchor point for the X axis
   * @param anchorY the anchor point for the Y axis
   * @return this builder
   */
  public ColliderBuilder anchor(ColliderAnchor anchorX, ColliderAnchor anchorY) {
    this.anchorX = anchorX;
    this.anchorY = anchorY;
    return this;
  }

  /**
   * Sets the same anchor for both X and Y axes.
   *
   * @param anchor the anchor point
   * @return this builder
   * @see #anchor(ColliderAnchor, ColliderAnchor)
   */
  public ColliderBuilder anchor(ColliderAnchor anchor) {
    return anchor(anchor, anchor);
  }

  /**
   * Builds the final {@link Collider} based on the specified parameters.
   *
   * @return the built collider
   */
  public Collider build() {
    if (size == null) {
      throw new IllegalStateException("Size must be set before building a collider.");
    }

    Size scaledSize = size.copy();
    scaledSize.scale(scaleX, scaleY);

    Collider collider = new Collider(new Position(0, 0), scaledSize);

    int offsetX = switch (anchorX) {
      case LEFT -> 0;
      case RIGHT -> size.getWidth() - scaledSize.getWidth();
      default -> (size.getWidth() - scaledSize.getWidth()) / 2;
    };

    int offsetY = switch (anchorY) {
      case TOP -> 0;
      case BOTTOM -> size.getHeight() - scaledSize.getHeight();
      default -> (size.getHeight() - scaledSize.getHeight()) / 2;
    };

    collider.setOffset(offsetX, offsetY);
    return collider;
  }
}
