package io.tidalisland.engine;

import io.tidalisland.config.Config;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The game.
 */
public class Game implements Runnable {
  private GameCanvas gamePanel;
  private Thread gameThread;
  private boolean running;

  public Game(GameCanvas gamePanel) {
    this.gamePanel = gamePanel;
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
        gamePanel.update();
        render(bs);
        gamePanel.endFrame();
        delta--;
      }
    }
  }

  /**
   * Renders the game.
   */
  private void render(BufferStrategy bs) {
    do {
      do {
        Graphics g = bs.getDrawGraphics();
        try {
          g.clearRect(0, 0, Config.screenWidth(), Config.screenHeight());
          gamePanel.render(g);
        } finally {
          g.dispose();
        }
      } while (bs.contentsRestored());
      bs.show();
    } while (bs.contentsLost());
  }
}
