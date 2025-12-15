package io.tidalisland.ui.components;

import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Graphics;

/**
 * A button.
 */
public class UiButton extends UiPanel {

  private UiLabel label;

  /**
   * Creates a button.
   */
  public UiButton(String text, int width, int height, int x, int y) {
    super(width, height, x, y);
    style = UiStyleDirector.makePrimary();

    label = new UiLabel(text, width, height, 0, 0);
    add(label);
  }

  public UiButton(String text, int width, int height) {
    this(text, width, height, 0, 0);
  }

  @Override
  protected void onUpdate(KeyHandler keys, MouseHandler mouse) {
    super.onUpdate(keys, mouse);
  }

  @Override
  protected void onRender(Graphics g) {
    super.onRender(g);
    label.render(g);
  }

  public void setText(String text) {
    label.setText(text);
  }

  public String getText() {
    return label.getText();
  }

  public UiLabel getLabel() {
    return label;
  }

  public void setLabel(UiLabel label) {
    this.label = label;
  }
}
