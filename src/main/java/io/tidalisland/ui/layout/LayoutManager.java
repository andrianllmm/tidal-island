package io.tidalisland.ui.layout;

import io.tidalisland.ui.components.UiComponent;

/**
 * Manages layout of {@link UiComponent}s inside a container.
 */
public abstract class LayoutManager {
  protected HorizontalAlignment horAlign = HorizontalAlignment.LEFT;
  protected VerticalAlignment verAlign = VerticalAlignment.TOP;

  /**
   * Layouts the children of a parent component.
   *
   * @param parent the parent component
   */
  public abstract void layout(UiComponent parent);

  /**
   * Sets the horizontal and vertical alignment.
   *
   * @param horAlign the horizontal alignment
   * @param verAlign the vertical alignment
   */
  public void setAlignment(HorizontalAlignment horAlign, VerticalAlignment verAlign) {
    this.horAlign = horAlign;
    this.verAlign = verAlign;
  }

  /**
   * Sets the horizontal alignment.
   *
   * @param horAlign the horizontal alignment
   */
  public void setAlignment(HorizontalAlignment horAlign) {
    this.horAlign = horAlign;
  }

  /**
   * Sets the vertical alignment.
   *
   * @param verAlign the vertical alignment
   */
  public void setAlignment(VerticalAlignment verAlign) {
    this.verAlign = verAlign;
  }
}
