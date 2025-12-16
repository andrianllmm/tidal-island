package io.tidalisland.engine;

import io.tidalisland.config.Config;
import io.tidalisland.input.KeyHandler;
import io.tidalisland.input.MouseHandler;
import io.tidalisland.states.GameStateManager;
import io.tidalisland.states.PlayingState;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The game canvas.
 */
public class GameCanvas extends Canvas {

  private KeyHandler keys;
  private MouseHandler mouse;
  private GameStateManager gsm;

  private Runnable onToggleFullscreen;

  /**
   * Initializes the canvas.
   */
  public GameCanvas() {
    setPreferredSize(new Dimension(Config.screenWidth(), Config.screenHeight()));
    setMinimumSize(new Dimension(Config.screenWidth() / 2, Config.screenHeight() / 2));
    setBackground(Color.BLACK);
    setIgnoreRepaint(true);

    keys = new KeyHandler();
    mouse = new MouseHandler();

    addKeyListener(keys);
    addMouseListener(mouse);
    addMouseMotionListener(mouse);
    addMouseWheelListener(mouse);

    setFocusable(true);
    requestFocus();
    requestFocusInWindow();

    gsm = new GameStateManager();
    gsm.push(new PlayingState(gsm, keys, mouse));
  }

  /**
   * Updates the game.
   */
  public void update() {
    gsm.update();
    if (keys.isJustPressed("toggle_fullscreen") && onToggleFullscreen != null) {
      onToggleFullscreen.run();
    }
  }

  /**
   * Renders the game.
   */
  protected void render(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    gsm.render(g2);
  }

  /**
   * Called at the end of each frame.
   */
  public void endFrame() {
    keys.endFrame();
    mouse.endFrame();
  }

  public void setOnToggleFullscreen(Runnable callback) {
    this.onToggleFullscreen = callback;
  }
}
