package io.tidalisland.ui.styles;

import java.awt.Color;
import java.awt.Font;

/**
 * Immutable style definition for UI components.
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

  private UiStyle(Color background, Color hoverBackground, Color pressedBackground, Color textColor,
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

  /** Creates the default blank style. */
  public static UiStyle create() {
    return new UiStyle(new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0),
        Color.WHITE, Color.WHITE, new Color(0, 0, 0, 0), 0, 0,
        new Font("Press Start 2P", Font.BOLD, 16), 0, 0);
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

  public UiStyle bg(Color v) {
    return new UiStyle(v, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, cornerRadius, font, paddingX, paddingY);
  }

  public UiStyle hoverBg(Color v) {
    return new UiStyle(bg, v, pressedBg, textColor, disabledTextColor, borderColor, borderWidth,
        cornerRadius, font, paddingX, paddingY);
  }

  public UiStyle pressedBg(Color v) {
    return new UiStyle(bg, hoverBg, v, textColor, disabledTextColor, borderColor, borderWidth,
        cornerRadius, font, paddingX, paddingY);
  }

  public UiStyle textColor(Color v) {
    return new UiStyle(bg, hoverBg, pressedBg, v, disabledTextColor, borderColor, borderWidth,
        cornerRadius, font, paddingX, paddingY);
  }

  public UiStyle disabledTextColor(Color v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, v, borderColor, borderWidth, cornerRadius,
        font, paddingX, paddingY);
  }

  public UiStyle borderColor(Color v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, v, borderWidth,
        cornerRadius, font, paddingX, paddingY);
  }

  public UiStyle borderWidth(int v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor, v,
        cornerRadius, font, paddingX, paddingY);
  }

  public UiStyle cornerRadius(int v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, v, font, paddingX, paddingY);
  }

  public UiStyle font(Font v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, cornerRadius, v, paddingX, paddingY);
  }

  public UiStyle fontName(String name) {
    return font(new Font(name, font.getStyle(), font.getSize()));
  }

  public UiStyle fontStyle(int style) {
    return font(new Font(font.getName(), style, font.getSize()));
  }

  public UiStyle fontSize(int size) {
    return font(new Font(font.getName(), font.getStyle(), size));
  }

  public UiStyle paddingX(int v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, cornerRadius, font, v, paddingY);
  }

  public UiStyle paddingY(int v) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, cornerRadius, font, paddingX, v);
  }

  public UiStyle padding(int p) {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, cornerRadius, font, p, p);
  }
}
