package io.tidalisland.ui.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel that arranges children in a grid automatically using fixed cell size.
 */
public class UiGridPanel extends UiComponent {
  private final List<UiComponent> children = new ArrayList<>();
  private final int rows;
  private final int cols;
  private final int gap;
  private final int cellWidth;
  private final int cellHeight;

  /**
   * Initializes a new grid panel.
   */
  public UiGridPanel(int x, int y, int cellWidth, int cellHeight, int rows, int cols, int gap) {
    super(x, y, cols * cellWidth + (cols - 1) * gap, rows * cellHeight + (rows - 1) * gap);
    this.cellWidth = cellWidth;
    this.cellHeight = cellHeight;
    this.rows = rows;
    this.cols = cols;
    this.gap = gap;
  }

  /**
   * Adds a child component to this grid.
   */
  public void add(UiComponent component) {
    children.add(component);
    updateLayout();
  }

  /**
   * Removes all child components from this grid.
   */
  public void clear() {
    children.clear();
    updateLayout();
  }

  @Override
  public void update() {
    if (!visible) {
      return;
    }

    for (UiComponent c : children) {
      if (c.isVisible()) {
        c.update();
      }
    }

    updateLayout();
  }

  @Override
  public void render(Graphics2D g) {
    if (!visible) {
      return;
    }

    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(x, y);

    for (UiComponent c : children) {
      if (c.isVisible()) {
        c.render(g2);
      }
    }

    g2.dispose();
  }

  /**
   * Arranges children into grid cells.
   */
  private void updateLayout() {
    for (int i = 0; i < children.size(); i++) {
      UiComponent c = children.get(i);
      int row = i / cols;
      int col = i % cols;
      c.x = col * (cellWidth + gap);
      c.y = row * (cellHeight + gap);
      c.width = cellWidth;
      c.height = cellHeight;
    }
  }
}
