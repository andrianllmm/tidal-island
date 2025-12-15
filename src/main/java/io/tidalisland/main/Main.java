package io.tidalisland.main;

import io.tidalisland.engine.Game;
import io.tidalisland.engine.GameCanvas;
import io.tidalisland.engine.GameWindow;

/**
 * Main entry point.
 */
public class Main {

  /**
   * Launches the game.
   */
  public static void main(final String[] args) {
    GameCanvas canvas = new GameCanvas();
    Game game = new Game(canvas);
    GameWindow window = new GameWindow(game, canvas);
    window.setVisible(true);
    game.start();
  }
}
