package io.tidalisland.ui.layout;

import io.tidalisland.ui.components.UiComponent;

/**
 * Manages layout (positions) of children inside a container.
 */
public abstract class LayoutManager {
  protected HorizontalAlignment horAlign = HorizontalAlignment.LEFT;
  protected VerticalAlignment verAlign = VerticalAlignment.TOP;

  public abstract void layout(UiComponent parent);

  public void setAlignment(HorizontalAlignment horAlign, VerticalAlignment verAlign) {
    this.horAlign = horAlign;
    this.verAlign = verAlign;
  }

  public void setAlignment(HorizontalAlignment horAlign) {
    this.horAlign = horAlign;
  }

  public void setAlignment(VerticalAlignment verAlign) {
    this.verAlign = verAlign;
  }
}
