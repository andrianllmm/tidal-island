package io.tidalisland.states;

import io.tidalisland.config.Config;
import io.tidalisland.engine.GameClock;
import io.tidalisland.ui.components.UiLabel;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Game over state.
 */
public class GameOverState implements GameState {

  private final GameStateManager gsm;

  public GameOverState(GameStateManager gsm) {
    this.gsm = gsm;
  }

  @Override
  public void onEnter() {
    GameClock.getInstance().setPaused(true); // pause game systems
  }

  @Override
  public void onExit() {
    GameClock.getInstance().setPaused(false);
  }

  @Override
  public void update() {}

  @Override
  public void render(Graphics g) {
    UiPanel panel = new UiPanel(Config.screenWidth(), Config.screenHeight());
    panel.setStyle(UiStyleDirector.makeTransparent());
    panel.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
    panel.style(s -> s.bg(new Color(0, 0, 0, 180)));

    UiLabel label = new UiLabel("GAME OVER", Config.screenWidth(), Config.screenHeight());
    label.setStyle(UiStyleDirector.makeTransparent());
    label.style(s -> s.fontSize(32));
    panel.add(label);

    panel.render(g);
  }
}
