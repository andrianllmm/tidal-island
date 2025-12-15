package io.tidalisland.ui.layout;

import io.tidalisland.ui.components.UiComponent;

/**
 * Positions children in a grid.
 */
public class GridLayout extends LayoutManager {

  private final int columns;
  private final int cellWidth;
  private final int cellHeight;
  private final int spacingX;
  private final int spacingY;

  /**
   * Creates a grid layout.
   *
   * @param columns the number of columns
   * @param cellWidth the width of each cell
   * @param cellHeight the height of each cell
   * @param spacingX the horizontal spacing between cells
   * @param spacingY the vertical spacing between cells
   */
  public GridLayout(int columns, int cellWidth, int cellHeight, int spacingX, int spacingY) {
    this.columns = columns;
    this.cellWidth = cellWidth;
    this.cellHeight = cellHeight;
    this.spacingX = spacingX;
    this.spacingY = spacingY;
  }

  @Override
  public void layout(UiComponent parent) {
    int padX = parent.getStyle().getPaddingX();
    int padY = parent.getStyle().getPaddingY();

    // Track current position in the grid
    int currentX = padX;
    int currentY = padY;
    int colIndex = 0;

    for (UiComponent child : parent.getChildren()) {
      // Position child inside its cell
      int childX = computeCellX(child, currentX);
      int childY = computeCellY(child, currentY);

      child.setX(childX);
      child.setY(childY);

      // Enforce uniform cell size
      child.setWidth(cellWidth);
      child.setHeight(cellHeight);

      // Move to next cell (wrap around if necessary)
      colIndex++;
      if (colIndex >= columns) {
        colIndex = 0;
        currentX = padX;
        currentY += cellHeight + spacingY;
      } else {
        currentX += cellWidth + spacingX;
      }
    }

    // Compute number of rows actually used
    int numRows = (parent.getChildren().size() + columns - 1) / columns;
    int requiredHeight = numRows * cellHeight + (numRows - 1) * spacingY + padY * 2;

    // Resize parent height if children overflow
    if (requiredHeight > parent.getHeight()) {
      parent.setHeight(requiredHeight);
    }

    // Align entire grid inside parent
    alignGridWithinParent(parent, padX, padY);
  }

  /** Computes X coordinate of a child within its cell based on horizontal alignment. */
  private int computeCellX(UiComponent child, int cellStartX) {
    return switch (horAlign) {
      case LEFT -> cellStartX;
      case CENTER -> cellStartX + (cellWidth - child.getWidth()) / 2;
      case RIGHT -> cellStartX + (cellWidth - child.getWidth());
      default -> cellStartX;
    };
  }

  /** Computes Y coordinate of a child within its cell based on vertical alignment. */
  private int computeCellY(UiComponent child, int cellStartY) {
    return switch (verAlign) {
      case TOP -> cellStartY;
      case CENTER -> cellStartY + (cellHeight - child.getHeight()) / 2;
      case BOTTOM -> cellStartY + (cellHeight - child.getHeight());
      default -> cellStartY;
    };
  }

  /** Aligns the entire grid inside the parent based on alignment settings. */
  private void alignGridWithinParent(UiComponent parent, int padX, int padY) {
    int numRows = (parent.getChildren().size() + columns - 1) / columns;
    int gridWidth = columns * cellWidth + (columns - 1) * spacingX;
    int gridHeight = numRows * cellHeight + (numRows - 1) * spacingY;

    int offsetX = switch (horAlign) {
      case CENTER -> (parent.getWidth() - padX * 2 - gridWidth) / 2;
      case RIGHT -> parent.getWidth() - padX * 2 - gridWidth;
      default -> 0; // LEFT
    };

    int offsetY = switch (verAlign) {
      case CENTER -> (parent.getHeight() - padY * 2 - gridHeight) / 2;
      case BOTTOM -> parent.getHeight() - padY * 2 - gridHeight;
      default -> 0; // TOP
    };

    for (UiComponent child : parent.getChildren()) {
      child.setX(child.getX() + offsetX);
      child.setY(child.getY() + offsetY);
    }
  }
}
