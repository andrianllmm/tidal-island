package io.tidalisland.ui.components;

import static io.tidalisland.config.Config.UI_FONT;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Displays a text label in the UI.
 */
public class UiLabel extends UiComponent {
  private String text;
  private Color color;
  private Font font;

  /**
   * Initializes a new label.
   */
  public UiLabel(String text, int x, int y) {
    super(x, y, 0, 0);
    this.text = text;
    this.color = Color.WHITE;
    this.font = UI_FONT;
  }

  public UiLabel(String text) {
    this(text, 0, 0);
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void setFont(Font font) {
    this.font = font;
  }

  @Override
  public void update() {}

  @Override
  public void render(Graphics2D g) {
    g.setFont(font);
    g.setColor(color);
    FontMetrics fm = g.getFontMetrics();
    width = fm.stringWidth(text);
    height = fm.getHeight();
    g.drawString(text, x, y + fm.getAscent());
  }
}
