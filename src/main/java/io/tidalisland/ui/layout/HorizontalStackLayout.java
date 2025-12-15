package io.tidalisland.ui.layout;

import io.tidalisland.ui.components.UiComponent;

/**
 * Positions children horizontally.
 */
public class HorizontalStackLayout extends LayoutManager {
  private final int spacing;

  /**
   * Creates a horizontal stack layout.
   *
   * @param spacing the spacing between children
   */
  public HorizontalStackLayout(int spacing) {
    this.spacing = spacing;
  }

  @Override
  public void layout(UiComponent parent) {
    int padX = parent.getStyle().getPaddingX();
    int padY = parent.getStyle().getPaddingY();

    int totalWidth = 0;
    int maxChildHeight = parent.getHeight() - padY * 2;
    for (UiComponent child : parent.getChildren()) {
      if (child.getHeight() > maxChildHeight) {
        child.setHeight(maxChildHeight); // cap height
      }
      totalWidth += child.getWidth() + spacing; // calculate total width
    }
    if (!parent.getChildren().isEmpty()) {
      totalWidth -= spacing; // remove last spacing
    }

    // Resize parent width if children overflow
    if (totalWidth + padX * 2 > parent.getWidth()) {
      parent.setWidth(totalWidth + padX * 2);
    }

    // Compute horizontal offset (for all children)
    int offsetX = switch (horAlign) {
      case LEFT -> 0;
      case CENTER -> (parent.getWidth() - totalWidth) / 2;
      case RIGHT -> parent.getWidth() - totalWidth - padX;
      default -> 0;
    };

    int currentX = padX + offsetX;

    for (UiComponent child : parent.getChildren()) {
      // Compute vertical offset (unique for each child)
      int childY = switch (verAlign) {
        case TOP -> padY;
        case CENTER -> padY + (parent.getHeight() - padY * 2 - child.getHeight()) / 2;
        case BOTTOM -> parent.getHeight() - padY - child.getHeight();
        default -> padY;
      };

      child.setX(currentX);
      child.setY(childY);

      // Move to next position
      currentX += child.getWidth() + spacing;
    }
  }
}
