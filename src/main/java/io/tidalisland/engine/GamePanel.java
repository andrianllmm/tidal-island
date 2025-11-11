package io.tidalisland.engine;

import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;

import io.tidalisland.entities.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * The game panel.
 */
public class GamePanel extends JPanel {
  private KeyHandler keyH;

  private Player player;

  /**
   * Sets up the panel.
   */
  public GamePanel() {
    setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    setBackground(Color.BLACK);
    setDoubleBuffered(true);

    keyH = new KeyHandler();
    addKeyListener(keyH);
    setFocusable(true);

    player = new Player(keyH);
  }

  /**
   * Updates the game.
   */
  public void update() {
    player.update();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    try {
      player.draw(g2);
    } finally {
      g2.dispose();
    }
  }
}
