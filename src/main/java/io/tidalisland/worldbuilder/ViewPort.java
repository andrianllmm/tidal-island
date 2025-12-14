package io.tidalisland.worldbuilder;

/**
 * Manages the viewport/camera position for the map editor.
 */
public class ViewPort {
  private int x = 0;
  private int y = 0;
  private int cols = 20;
  private int rows = 20;

  private final int maxCols;
  private final int maxRows;

  private static final int MIN_VIEW_SIZE = 10;
  private static final int MAX_VIEW_COLS = 50;
  private static final int MAX_VIEW_ROWS = 50;

  private Runnable onChange;

  public ViewPort(int mapWidth, int mapHeight) {
    this.maxCols = mapWidth;
    this.maxRows = mapHeight;
  }

  /**
   * Zooms the view by a given delta.
   */
  public void zoom(int delta) {
    int newCols = cols + delta;
    int newRows = newCols;

    if (newCols < MIN_VIEW_SIZE || newCols > MAX_VIEW_COLS) {
      return;
    }
    if (newRows < MIN_VIEW_SIZE || newRows > MAX_VIEW_ROWS) {
      return;
    }

    // Center focus
    x += (cols - newCols) / 2;
    y += (rows - newRows) / 2;

    cols = newCols;
    rows = newRows;

    clampPosition();
    notifyChange();
  }

  /**
   * Moves the view to a given position.
   */
  public void moveTo(int newX, int newY) {
    this.x = newX;
    this.y = newY;
    clampPosition();
  }

  /** Adds a listener that is notified when the view changes. */
  public void setOnChange(Runnable listener) {
    this.onChange = listener;
  }

  /** Notifies all listeners that the view has changed. */
  private void notifyChange() {
    if (onChange != null) {
      onChange.run();
    }
  }

  private void clampPosition() {
    x = Math.max(0, Math.min(maxCols - cols, x));
    y = Math.max(0, Math.min(maxRows - rows, y));
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getCols() {
    return cols;
  }

  public int getRows() {
    return rows;
  }
}