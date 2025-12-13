package io.tidalisland.main;

import io.tidalisland.engine.GameLoop;
import io.tidalisland.engine.GamePanel;
import javax.swing.JFrame;

/**
 * Main entry point.
 */
public class Main {
  /**
   * Launches the game.
   */
  public static void main(final String[] args) {
    JFrame window = new JFrame("Tidal Island");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);
    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    GameLoop gameLoop = new GameLoop(gamePanel);
    gameLoop.start();
  }
}
