package io.tidalisland.engine;

import io.tidalisland.config.Config;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * The game.
 */
public class Game implements Runnable {

  private GameCanvas gamePanel;
  private Thread gameThread;
  private boolean running;

  /** Off-screen buffer for rendering at native resolution. */
  private BufferedImage gameBuffer;

  /**
   * Creates a new game.
   */
  public Game(GameCanvas gamePanel) {
    this.gamePanel = gamePanel;
    this.gameBuffer = new BufferedImage(
        Config.screenWidth(), Config.screenHeight(), BufferedImage.TYPE_INT_ARGB);
  }

  /**
   * Starts the game.
   */
  public void start() {
    if (running) {
      return;
    }
    running = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  /**
   * Stops the game.
   */
  public void stop() {
    running = false;
    try {
      gameThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Runs the game.
   */
  @Override
  public void run() {
    gamePanel.createBufferStrategy(2);
    BufferStrategy bs = gamePanel.getBufferStrategy();

    double interval = 1_000_000_000 / Config.fps(); // nanoseconds per frame
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    while (running) {
      if (!gamePanel.isDisplayable()) {
        break;
      }

      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / interval;
      lastTime = currentTime;

      if (delta >= 1) {
        GameClock.getInstance().update();
        gamePanel.update();
        gamePanel.endFrame();
        render(bs);
        delta--;
      }
    }
  }

  /**
   * Renders the game.
   */
  private void render(BufferStrategy bs) {
    // Render game to off-screen buffer at native resolution
    Graphics2D bufferGraphics = gameBuffer.createGraphics();
    try {
      // Clear buffer
      bufferGraphics.setColor(Color.BLACK);
      bufferGraphics.fillRect(0, 0, Config.screenWidth(), Config.screenHeight());

      // Render game to buffer
      gamePanel.render(bufferGraphics);

    } finally {
      bufferGraphics.dispose();
    }

    // Scale buffer to screen with letterboxing
    do {
      do {
        Graphics g = bs.getDrawGraphics();
        try {
          Graphics2D g2 = (Graphics2D) g;

          // Clear entire screen with black (letterbox)
          g2.setColor(Color.BLACK);
          g2.fillRect(0, 0, Config.windowWidth(), Config.windowHeight());

          // Set scaling quality
          g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
              RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
          g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

          // Draw scaled buffer to viewport
          g2.drawImage(gameBuffer, Config.viewportX(), Config.viewportY(), Config.viewportWidth(),
              Config.viewportHeight(), null);

        } finally {
          g.dispose();
        }

      } while (bs.contentsRestored());

      bs.show();
    } while (bs.contentsLost());
  }
}
