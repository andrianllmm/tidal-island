package io.tidalisland.states;

import io.tidalisland.config.Config;
import io.tidalisland.engine.GameClock;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.UiGameOverMenu;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Graphics;

/**
 * Game over state.
 */
public class GameOverState implements GameState {

  private final GameStateManager gsm;
  private final KeyHandler keys;
  private final MouseHandler mouse;

  private UiPanel ui;

  /**
   * Initializes the game over state.
   */
  public GameOverState(GameStateManager gsm, KeyHandler keys, MouseHandler mouse, boolean win) {
    this.gsm = gsm;
    this.keys = keys;
    this.mouse = mouse;

    ui = new UiPanel(Config.screenWidth(), Config.screenHeight(), 0, 0);
    ui.setStyle(UiStyleDirector.fromTransparent().padding(24).build());
    ui.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    String title = win ? "YOU WIN!" : "YOU LOSE!";
    UiGameOverMenu menu = new UiGameOverMenu(400, 300, title);

    menu.onNewGame(() -> {
      gsm.set(new PlayingState(gsm, keys, mouse));
    });

    menu.onExit(() -> {
      gsm.push(new TitleState(gsm, keys, mouse));
    });

    ui.add(menu);
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
    ui.update(keys, mouse);
  }

  @Override
  public void render(Graphics g) {
    ui.render(g);
  }
}
