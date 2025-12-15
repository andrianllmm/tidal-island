package io.tidalisland.ui.styles;

import java.awt.Color;

/**
 * Director for {@link UiStyle}s.
 */
public final class UiStyleDirector {

  private UiStyleDirector() {}

  /** Creates a primary style. */
  public static UiStyle makePrimary() {
    return new UiStyleBuilder().bg(new Color(60, 60, 60)).hoverBg(new Color(80, 80, 80))
        .pressedBg(new Color(40, 40, 40)).textColor(new Color(220, 220, 220))
        .borderColor(new Color(45, 45, 45)).borderWidth(3).cornerRadius(10).padding(4).build();
  }

  /** Creates a panel style. */
  public static UiStyle makePanel() {
    return new UiStyleBuilder().bg(new Color(70, 70, 70)).hoverBg(new Color(70, 70, 70))
        .pressedBg(new Color(70, 70, 70)).textColor(new Color(220, 220, 220))
        .borderColor(new Color(55, 55, 55)).borderWidth(3).cornerRadius(10).padding(4).build();
  }

  /** Creates a transparent style. */
  public static UiStyle makeTransparent() {
    return new UiStyleBuilder().bg(new Color(0, 0, 0, 0)).hoverBg(new Color(0, 0, 0, 0))
        .pressedBg(new Color(0, 0, 0, 0)).textColor(new Color(220, 220, 220))
        .borderColor(new Color(0, 0, 0, 0)).borderWidth(0).cornerRadius(0).padding(0).build();
  }

  // Convenience method for getting builders.

  public static UiStyleBuilder fromPrimary() {
    return UiStyleBuilder.from(makePrimary());
  }

  public static UiStyleBuilder fromPanel() {
    return UiStyleBuilder.from(makePanel());
  }

  public static UiStyleBuilder fromTransparent() {
    return UiStyleBuilder.from(makeTransparent());
  }
}
