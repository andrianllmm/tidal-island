package io.tidalisland.collision;

import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;

/**
 * Factory for creating colliders.
 */
public class ColliderFactory {
  /**
   * Creates a collider with a specified scale and anchor point.
   */
  public static Collider create(Size containerSize, double scaleX, double scaleY,
      ColliderAnchor anchorX, ColliderAnchor anchorY) {
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
