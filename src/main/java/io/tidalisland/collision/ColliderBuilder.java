package io.tidalisland.collision;

import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;

/**
 * Builder for creating {@link Collider} instances.
 */
public class ColliderBuilder {
  private Size containerSize;
  private double scaleX = 1.0;
  private double scaleY = 1.0;
  private ColliderAnchor anchorX = ColliderAnchor.CENTER;
  private ColliderAnchor anchorY = ColliderAnchor.CENTER;

  /**
   * Sets the container size which the collider will be based on.
   */
  public ColliderBuilder container(Size containerSize) {
    this.containerSize = containerSize;
    return this;
  }

  /**
   * Sets the scaling factors for X and Y axes.
   */
  public ColliderBuilder scale(double scaleX, double scaleY) {
    this.scaleX = scaleX;
    this.scaleY = scaleY;
    return this;
  }

  /**
   * Sets uniform scaling for both axes.
   */
  public ColliderBuilder scale(double scale) {
    return scale(scale, scale);
  }

  /**
   * Sets anchor points for both X and Y axes.
   */
  public ColliderBuilder anchor(ColliderAnchor anchorX, ColliderAnchor anchorY) {
    this.anchorX = anchorX;
    this.anchorY = anchorY;
    return this;
  }

  /**
   * Sets the same anchor for both X and Y axes.
   */
  public ColliderBuilder anchor(ColliderAnchor anchor) {
    return anchor(anchor, anchor);
  }

  /**
   * Builds the final {@link Collider} based on the specified parameters.
   */
  public Collider build() {
    if (containerSize == null) {
      throw new IllegalStateException("Container size must be set before building a collider.");
    }

    Size scaledSize = containerSize.copy();
    scaledSize.scale(scaleX, scaleY);

    Collider collider = new Collider(new Position(0, 0), scaledSize);

    int offsetX = switch (anchorX) {
      case LEFT -> 0;
      case RIGHT -> containerSize.getWidth() - scaledSize.getWidth();
      default -> (containerSize.getWidth() - scaledSize.getWidth()) / 2;
    };

    int offsetY = switch (anchorY) {
      case TOP -> 0;
      case BOTTOM -> containerSize.getHeight() - scaledSize.getHeight();
      default -> (containerSize.getHeight() - scaledSize.getHeight()) / 2;
    };

    collider.setOffset(offsetX, offsetY);
    return collider;
  }
}
