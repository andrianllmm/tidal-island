package io.tidalisland.engine;

import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The game panel.
 */
public class GamePanel extends JPanel {
  /**
   * Sets up the panel.
   */
  public GamePanel() {
    setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    setBackground(Color.BLACK);
    setDoubleBuffered(true);
  }

  /**
   * Updates the game.
   */
  public void update() {

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
