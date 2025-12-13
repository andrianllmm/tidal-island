package io.tidalisland.engine;

import javax.swing.JFrame;

/**
 * The game window.
 */
public class GameWindow extends JFrame {
  /**
   * Creates a new game window.
   */
  public GameWindow() {
    super("Tidal Island");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(true);
    setLocationRelativeTo(null);
  }

  public void addPanel(GamePanel panel) {
    add(panel);
    pack();
  }

  public void showWindow() {
    setVisible(true);
  }
}
