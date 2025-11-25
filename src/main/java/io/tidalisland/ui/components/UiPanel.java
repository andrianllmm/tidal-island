package io.tidalisland.ui.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for other UI components.
 */
public class UiPanel extends UiComponent {

  private final List<UiComponent> children = new ArrayList<>();

  private Color bgColor;
  private Color borderColor = null;
  private int borderWidth = 0;
  private boolean rounded = false;
  private int cornerRadius = 8;

  /**
   * Creates a panel with background color and default settings.
   */
  public UiPanel(int x, int y, int width, int height, Color bgColor) {
    super(x, y, width, height);
    this.bgColor = bgColor;
  }

  public UiPanel(int x, int y, int width, int height) {
    this(x, y, width, height, new Color(50, 50, 50, 200));
  }

  public UiPanel(int x, int y) {
    this(x, y, 0, 0);
  }

  /**
   * Adds a child component to this panel.
   */
  public void add(UiComponent component, boolean fill) {
    if (fill) {
      component.width = width;
      component.height = height;
    }
    children.add(component);
  }

  public void add(UiComponent component) {
    add(component, false);
  }

  /**
   * Sets border color and width.
   */
  public void setBorder(Color color, int width) {
    this.borderColor = color;
    this.borderWidth = width;
  }

  /**
   * Enables rounded corners with optional radius.
   */
  public void setRounded(boolean rounded, int radius) {
    this.rounded = rounded;
    this.cornerRadius = radius;
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
  }

  @Override
  public void render(Graphics2D g) {
    if (!visible) {
      return;
    }

    // Draw background
    if (bgColor != null) {
      g.setColor(bgColor);
      if (rounded) {
        g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
      } else {
        g.fillRect(x, y, width, height);
      }
    }

    // Draw border
    if (borderColor != null && borderWidth > 0) {
      g.setColor(borderColor);

      // Save old stroke
      var oldStroke = ((Graphics2D) g).getStroke();
      ((Graphics2D) g).setStroke(new BasicStroke(borderWidth));

      if (rounded) {
        g.drawRoundRect(x, y, width, height, cornerRadius, cornerRadius);
      } else {
        g.drawRect(x, y, width, height);
      }

      // Restore stroke
      ((Graphics2D) g).setStroke(oldStroke);
    }

    // Render children
    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(x, y);
    for (UiComponent c : children) {
      if (c.isVisible()) {
        c.render(g2);
      }
    }
    g2.dispose();
  }
}
