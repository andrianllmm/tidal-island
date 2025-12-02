package io.tidalisland.ui.components;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.styles.UiStyle;
import io.tidalisland.ui.styles.UiStyleBuilder;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Base class for all UI components in the game.
 */
public abstract class UiComponent {
  // Position and size
  protected int x;
  protected int y;
  protected int width;
  protected int height;

  // Component state
  protected boolean visible = true; // is visible on screen
  protected boolean enabled = true; // can be interacted with

  // Interaction states
  protected boolean hovered = false; // mouse is over component
  protected boolean pressed = false; // mouse is down over component

  // Style
  protected UiStyle style;

  // Events
  protected Runnable onClick;

  // For nesting components
  protected UiComponent parent;
  protected final List<UiComponent> children = new ArrayList<>();

  /**
   * Initializes a new component.
   */
  public UiComponent(int width, int height, int x, int y) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.style = new UiStyleBuilder().build();
  }

  public UiComponent(int width, int height) {
    this(width, height, 0, 0);
  }

  /**
   * Updates the component state.
   */
  public void update(KeyHandler keys, MouseHandler mouse) {
    if (!enabled) {
      return;
    }

    updateInteraction(mouse);

    onUpdate(keys, mouse);

    // Update children
    for (UiComponent child : children) {
      child.update(keys, mouse);
    }
  }

  /**
   * Subclass-specific update logic.
   */
  protected abstract void onUpdate(KeyHandler keys, MouseHandler mouse);

  /**
   * Renders the component.
   */
  public void render(Graphics2D g) {
    if (!visible) {
      return;
    }

    onRender(g);

    // Render children
    for (UiComponent child : children) {
      child.render(g);
    }
  }

  /**
   * Subclass-specific rendering logic.
   */
  protected abstract void onRender(Graphics2D g);

  /**
   * Updates the component's interaction state.
   */
  protected void updateInteraction(MouseHandler mouse) {
    hovered = contains(mouse.getX(), mouse.getY());
    pressed = hovered && mouse.isDown(MouseHandler.BTN_LEFT);

    if (mouse.isClick(MouseHandler.BTN_LEFT)) {
      int px = mouse.getPressX(MouseHandler.BTN_LEFT);
      int py = mouse.getPressY(MouseHandler.BTN_LEFT);
      int rx = mouse.getReleaseX(MouseHandler.BTN_LEFT);
      int ry = mouse.getReleaseY(MouseHandler.BTN_LEFT);

      if (contains(px, py) && contains(rx, ry)) {
        onClick();
      }
    }
  }

  /**
   * Hit detection.
   */
  public boolean contains(int mouseX, int mouseY) {
    int ax = getAbsX();
    int ay = getAbsY();
    return (mouseX >= ax && mouseX <= ax + width) && (mouseY >= ay && mouseY <= ay + height);
  }

  /** Apply multiple style edits at once using a lambda. */
  public void style(UnaryOperator<UiStyleBuilder> modifier) {
    UiStyleBuilder builder = UiStyleBuilder.from(style); // create a builder from current style
    style = modifier.apply(builder).build(); // apply modifications and rebuild
  }


  /**
   * Called when this component is clicked.
   */
  protected void onClick() {
    if (onClick != null) {
      onClick.run();
    }
  }

  public void setOnClick(Runnable onClick) {
    this.onClick = onClick;
  }

  public int getAbsX() {
    return parent == null ? x : parent.getAbsX() + x;
  }

  public int getAbsY() {
    return parent == null ? y : parent.getAbsY() + y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public UiStyle getStyle() {
    return style;
  }

  public void setStyle(UiStyle style) {
    this.style = style;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void toggleVisible() {
    setVisible(!isVisible());
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isHovered() {
    return hovered;
  }

  public boolean isPressed() {
    return pressed;
  }

  public UiComponent getParent() {
    return parent;
  }

  public void add(UiComponent child) {
    children.add(child);
    child.parent = this;
  }

  public void remove(UiComponent child) {
    children.remove(child);
    child.parent = null;
  }

  public List<UiComponent> getChildren() {
    return children;
  }
}
