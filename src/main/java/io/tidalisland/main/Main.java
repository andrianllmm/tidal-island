package io.tidalisland.main;

import io.tidalisland.engine.GameLoop;
import io.tidalisland.engine.GamePanel;
import io.tidalisland.engine.GameWindow;

/**
 * Main entry point.
 */
public class Main {
  /**
   * Launches the game.
   */
  public static void main(final String[] args) {
    GameWindow window = new GameWindow();

    GamePanel gamePanel = new GamePanel();
    window.addPanel(gamePanel);

    window.showWindow();

    GameLoop gameLoop = new GameLoop(gamePanel);
    gameLoop.start();
  }
}
