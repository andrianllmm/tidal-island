package io.tidalisland.ui.layout;

import io.tidalisland.ui.components.UiComponent;

/**
 * Positions children vertically.
 */
public class VerticalStackLayout extends LayoutManager {
  private final int spacing;

  /**
   * Creates a vertical stack layout.
   *
   * @param spacing the spacing between children
   */
  public VerticalStackLayout(int spacing) {
    this.spacing = spacing;
  }

  @Override
  public void layout(UiComponent parent) {
    int padX = parent.getStyle().getPaddingX();
    int padY = parent.getStyle().getPaddingY();

    int totalHeight = 0;
    int maxChildWidth = parent.getWidth() - padX * 2;
    for (UiComponent child : parent.getChildren()) {
      if (child.getWidth() > maxChildWidth) {
        child.setWidth(maxChildWidth); // cap width
      }
      totalHeight += child.getHeight() + spacing; // calculate total height
    }
    if (!parent.getChildren().isEmpty()) {
      totalHeight -= spacing; // remove last spacing
    }

    // Resize parent height if children overflow
    if (totalHeight + padY * 2 > parent.getHeight()) {
      parent.setHeight(totalHeight + padY * 2);
    }

    // Compute vertical offset (for all children)
    int offsetY = switch (verAlign) {
      case TOP -> 0;
      case CENTER -> (parent.getHeight() - totalHeight) / 2;
      case BOTTOM -> parent.getHeight() - totalHeight - padY;
      default -> 0;
    };

    int currentY = padY + offsetY;

    for (UiComponent child : parent.getChildren()) {
      // Compute horizontal offset (unique for each child)
      int childX = switch (horAlign) {
        case LEFT -> padX;
        case CENTER -> padX + (parent.getWidth() - padX * 2 - child.getWidth()) / 2;
        case RIGHT -> parent.getWidth() - padX - child.getWidth();
        default -> padX;
      };

      child.setX(childX);
      child.setY(currentY);

      // Move to next position
      currentY += child.getHeight() + spacing;
    }
  }
}
