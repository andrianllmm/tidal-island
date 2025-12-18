package io.tidalisland.ui.styles;

import java.awt.Color;
import java.awt.Font;

/**
 * Builder for {@link UiStyle}.
 */
public final class UiStyleBuilder {

  private Color bg = new Color(0, 0, 0, 0);
  private Color hoverBg = new Color(0, 0, 0, 0);
  private Color pressedBg = new Color(0, 0, 0, 0);

  private Color textColor = Colors.WHITE;
  private Color disabledTextColor = Colors.WHITE;

  private Color borderColor = new Color(0, 0, 0, 0);
  private int borderWidth = 0;
  private int cornerRadius = 0;

  private Font font = new Font("Press Start 2P", Font.BOLD, 16);

  private int paddingX = 0;
  private int paddingY = 0;

  public UiStyleBuilder bg(Color v) {
    this.bg = v;
    return this;
  }

  public UiStyleBuilder hoverBg(Color v) {
    this.hoverBg = v;
    return this;
  }

  public UiStyleBuilder pressedBg(Color v) {
    this.pressedBg = v;
    return this;
  }

  public UiStyleBuilder textColor(Color v) {
    this.textColor = v;
    return this;
  }

  public UiStyleBuilder disabledTextColor(Color v) {
    this.disabledTextColor = v;
    return this;
  }

  public UiStyleBuilder borderColor(Color v) {
    this.borderColor = v;
    return this;
  }

  public UiStyleBuilder borderWidth(int v) {
    this.borderWidth = v;
    return this;
  }

  public UiStyleBuilder cornerRadius(int v) {
    this.cornerRadius = v;
    return this;
  }

  public UiStyleBuilder font(Font v) {
    this.font = v;
    return this;
  }

  public UiStyleBuilder fontName(String name) {
    this.font = new Font(name, font.getStyle(), font.getSize());
    return this;
  }

  public UiStyleBuilder fontStyle(int style) {
    this.font = new Font(font.getName(), style, font.getSize());
    return this;
  }

  public UiStyleBuilder fontSize(int size) {
    this.font = new Font(font.getName(), font.getStyle(), size);
    return this;
  }

  public UiStyleBuilder paddingX(int p) {
    this.paddingX = p;
    return this;
  }

  public UiStyleBuilder paddingY(int p) {
    this.paddingY = p;
    return this;
  }

  /** Convenience method for setting uniform padding. */
  public UiStyleBuilder padding(int p) {
    this.paddingX = p;
    this.paddingY = p;
    return this;
  }

  /**
   * Builds a style.
   *
   * @return the built style
   */
  public UiStyle build() {
    return new UiStyle(bg, hoverBg, pressedBg, textColor, disabledTextColor, borderColor,
        borderWidth, cornerRadius, font, paddingX, paddingY);
  }

  /**
   * Creates a builder from an existing style.
   *
   * @param style the style to copy
   * @return the builder
   */
  public static UiStyleBuilder from(UiStyle style) {
    return new UiStyleBuilder().bg(style.getBg()).hoverBg(style.getHoverBg())
        .pressedBg(style.getPressedBg()).textColor(style.getTextColor())
        .disabledTextColor(style.getDisabledTextColor()).borderColor(style.getBorderColor())
        .borderWidth(style.getBorderWidth()).cornerRadius(style.getCornerRadius())
        .font(style.getFont()).paddingX(style.getPaddingX()).paddingY(style.getPaddingY());
  }
}

