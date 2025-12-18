package io.tidalisland.ui;

import io.tidalisland.ui.components.UiButton;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.layout.VerticalStackLayout;

/**
 * Generic game menu.
 */
public class UiGameOverMenu extends UiPanel {

  private final UiButton newGameBtn;
  private final UiButton quitBtn;

  /**
   * Creates a new menu.
   */
  public UiGameOverMenu(int width, int height, String title) {
    super(width, height);

    // Layout
    setLayout(new VerticalStackLayout(16));
    getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    style(s -> s.padding(18));

    UiLabel label = new UiLabel(title, width, 50);
    label.style(s -> s.fontSize(32));
    add(label);

    int btnWidth = width - 60;
    int btnHeight = 40;

    newGameBtn = new UiButton("New Game", btnWidth, btnHeight);
    quitBtn = new UiButton("Exit", btnWidth, btnHeight);

    add(newGameBtn);
    add(quitBtn);
  }

  public void onNewGame(Runnable action) {
    newGameBtn.setOnClick(action);
  }

  public void onExit(Runnable action) {
    quitBtn.setOnClick(action);
  }
}
