package io.tidalisland.states;

import io.tidalisland.config.Config;
import io.tidalisland.engine.GameClock;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Pause state.
 */
public class PauseState implements GameState {

  private final GameStateManager gsm;

  private final KeyHandler keys;

  public PauseState(GameStateManager gsm, KeyHandler keys) {
    this.gsm = gsm;
    this.keys = keys;
  }

  @Override
  public void onEnter() {
    GameClock.getInstance().setPaused(true);
  }

  @Override
  public void onExit() {
    GameClock.getInstance().setPaused(false);
  }

  @Override
  public void update() {
    if (keys.isJustPressed("pause")) {
      gsm.pop(); // resume
    }
  }

  @Override
  public void render(Graphics g) {
    UiPanel panel = new UiPanel(Config.screenWidth(), Config.screenHeight());
    panel.setStyle(UiStyleDirector.makeTransparent());
    panel.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    panel.style(s -> s.bg(new Color(0, 0, 0, 150))); // dim background

    UiLabel label = new UiLabel("PAUSED", Config.screenWidth(), Config.screenHeight());
    label.setStyle(UiStyleDirector.makeTransparent());
    label.style(s -> s.fontSize(32));
    panel.add(label);

    panel.render(g);
  }
}
