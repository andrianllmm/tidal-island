package io.tidalisland.ui.styles;

import java.awt.Color;

/**
 * UI styles.
 */
public final class UiStyles {
  public static final UiStyle PRIMARY =
      UiStyle.create().bg(new Color(60, 60, 60)).hoverBg(new Color(80, 80, 80))
          .pressedBg(new Color(40, 40, 40)).textColor(new Color(220, 220, 220))
          .borderColor(new Color(45, 45, 45)).borderWidth(3).cornerRadius(10).padding(4);

  public static final UiStyle PANEL =
      UiStyle.create().bg(new Color(70, 70, 70)).hoverBg(new Color(70, 70, 70))
          .pressedBg(new Color(70, 70, 70)).textColor(new Color(220, 220, 220))
          .borderColor(new Color(55, 55, 55)).borderWidth(3).cornerRadius(10).padding(4);

  public static final UiStyle TRANSPARENT =
      UiStyle.create().bg(new Color(0, 0, 0, 0)).hoverBg(new Color(0, 0, 0, 0))
          .pressedBg(new Color(0, 0, 0, 0)).textColor(new Color(220, 220, 220))
          .borderColor(new Color(0, 0, 0, 0)).borderWidth(0).cornerRadius(0).padding(0);
}
