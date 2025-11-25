package io.tidalisland.ui.components;

import java.awt.Graphics2D;

/**
 * Base class for all UI components.
 */
public abstract class UiComponent {
  protected int x;
  protected int y;
  protected int width;
  protected int height;
  protected boolean visible = true;

  /**
   * Initializes a new component.
   */
  public UiComponent(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Initializes a new component with size 0.
   */
  public UiComponent(int x, int y) {
    this(x, y, 0, 0);
  }

  /**
   * Updates the component state.
   */
  public abstract void update();

  /**
   * Renders the component.
   */
  public abstract void render(Graphics2D g);

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean contains(int mouseX, int mouseY) {
    return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
