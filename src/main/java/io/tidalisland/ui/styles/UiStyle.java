package io.tidalisland.ui.styles;

import io.tidalisland.ui.components.UiComponent;
import java.awt.Color;
import java.awt.Font;

/**
 * Immutable style definition for {@link UiComponent}s.
 */
public final class UiStyle {

  // Background colors
  private final Color bg;
  private final Color hoverBg;
  private final Color pressedBg;

  // Text colors
  private final Color textColor;
  private final Color disabledTextColor;

  // Border
  private final Color borderColor;
  private final int borderWidth;
  private final int cornerRadius;

  // Font
  private final Font font;

  // Padding
  private final int paddingX;
  private final int paddingY;

  /** Creates a new style. */
  public UiStyle(Color background, Color hoverBackground, Color pressedBackground, Color textColor,
      Color disabledTextColor, Color borderColor, int borderWidth, int cornerRadius, Font font,
      int paddingX, int paddingY) {
    this.bg = background;
    this.hoverBg = hoverBackground;
    this.pressedBg = pressedBackground;
    this.textColor = textColor;
    this.disabledTextColor = disabledTextColor;
    this.borderColor = borderColor;
    this.borderWidth = borderWidth;
    this.cornerRadius = cornerRadius;
    this.font = font;
    this.paddingX = paddingX;
    this.paddingY = paddingY;
  }

  public Color getBg() {
    return bg;
  }

  public Color getHoverBg() {
    return hoverBg;
  }

  public Color getPressedBg() {
    return pressedBg;
  }

  public Color getTextColor() {
    return textColor;
  }

  public Color getDisabledTextColor() {
    return disabledTextColor;
  }

  public Color getBorderColor() {
    return borderColor;
  }

  public int getBorderWidth() {
    return borderWidth;
  }

  public int getCornerRadius() {
    return cornerRadius;
  }

  public Font getFont() {
    return font;
  }

  public int getPaddingX() {
    return paddingX;
  }

  public int getPaddingY() {
    return paddingY;
  }
}
