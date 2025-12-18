package io.tidalisland.ui.components;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.layout.LayoutManager;
import io.tidalisland.ui.layout.VerticalStackLayout;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A container that groups UI components.
 */
public class UiPanel extends UiComponent {

  private LayoutManager layoutManager;
  private boolean batching = false;

  /**
   * Creates a panel.
   */
  public UiPanel(int width, int height, int x, int y) {
    super(width, height, x, y);
    style = UiStyleDirector.makePanel();
    layoutManager = new VerticalStackLayout(4);
  }

  public UiPanel(int width, int height) {
    this(width, height, 0, 0);
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
  }

  @Override
  protected void onRender(Graphics g) {
    layout();

    int ax = getAbsX();
    int ay = getAbsY();

    // Draw background
    Color bg = style.getBg();
    if (!enabled) {
      bg = style.getPressedBg().darker();
    } else if (pressed) {
      bg = style.getPressedBg();
    } else if (hovered) {
      bg = style.getHoverBg();
    }
    g.setColor(bg);

    int radius = style.getCornerRadius();
    if (radius > 0) {
      g.fillRoundRect(ax, ay, width, height, radius, radius);
    } else {
      g.fillRect(ax, ay, width, height);
    }

    // Draw border
    int borderWidth = style.getBorderWidth();
    g.setColor(style.getBorderColor());
    for (int i = 0; i < borderWidth; i++) {
      if (radius > 0) {
        g.drawRoundRect(ax + i, ay + i, width - 2 * i, height - 2 * i, radius, radius);
      } else {
        g.drawRect(ax + i, ay + i, width - 2 * i, height - 2 * i);
      }
    }
  }

  /**
   * Layouts children using the layout manager.
   */
  public void layout() {
    if (layoutManager != null && isLayoutDirty()) {
      layoutManager.layout(this);
      clearLayoutDirty();
    }
  }

  /**
   * Gets the layout manager.
   */
  public LayoutManager getLayout() {
    return layoutManager;
  }

  /**
   * Sets the layout manager.
   */
  public void setLayout(LayoutManager layoutManager) {
    this.layoutManager = layoutManager;
    invalidateLayout();
  }

  /**
   * Begins batching updates.
   */
  public void beginBatch() {
    batching = true;
  }

  /**
   * Ends batching updates.
   */
  public void endBatch() {
    batching = false;
    layout(); // layout once at the end
  }

  @Override
  public void setVisible(boolean v) {
    super.setVisible(v);
    if (v) {
      layout();
    }
  }

  @Override
  public void add(UiComponent child) {
    super.add(child);
    if (!batching) {
      invalidateLayout();
    }
  }

  @Override
  public void remove(UiComponent child) {
    super.remove(child);
    if (!batching) {
      invalidateLayout();
    }
  }
}
