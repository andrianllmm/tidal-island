package io.tidalisland.engine;

import static io.tidalisland.config.Config.SCREEN_HEIGHT;
import static io.tidalisland.config.Config.SCREEN_WIDTH;

import io.tidalisland.entities.Player;
import io.tidalisland.graphics.Camera;
import io.tidalisland.tiles.WorldMap;
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
  private Camera camera;
  private WorldMap worldMap;

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

    worldMap = new WorldMap();

    camera = new Camera();

    player = new Player(keyH);
  }

  /**
   * Updates the game.
   */
  public void update() {
    player.update();
    camera.update(player);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    try {
      worldMap.draw(g2, camera);
      player.draw(g2, camera);
    } finally {
      g2.dispose();
    }
  }
}
