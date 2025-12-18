package io.tidalisland.states;

import io.tidalisland.config.Config;
import io.tidalisland.engine.GameClock;
import io.tidalisland.input.Action;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.ui.UiPauseMenu;
import io.tidalisland.ui.components.UiPanel;
import io.tidalisland.ui.layout.HorizontalAlignment;
import io.tidalisland.ui.layout.VerticalAlignment;
import io.tidalisland.ui.styles.UiStyleDirector;
import java.awt.Graphics;

/**
 * Pause state.
 */
public class PauseState implements GameState {

  private final GameStateManager gsm;
  private final KeyHandler keys;
  private final MouseHandler mouse;

  private UiPanel ui;

  /**
   * Initializes the pause state.
   */
  public PauseState(GameStateManager gsm, KeyHandler keys, MouseHandler mouse) {
    this.gsm = gsm;
    this.keys = keys;
    this.mouse = mouse;

    ui = new UiPanel(Config.screenWidth(), Config.screenHeight(), 0, 0);
    ui.setStyle(UiStyleDirector.fromTransparent().padding(24).build());
    ui.getLayout().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

    UiPauseMenu menu = new UiPauseMenu(400, 300);

    menu.onResume(() -> {
      gsm.pop(); // resume
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
    if (keys.isJustPressed(Action.PAUSE)) {
      gsm.pop(); // resume
    }
  }

  @Override
  public void render(Graphics g) {
    ui.render(g);
  }
}
