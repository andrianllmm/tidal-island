package io.tidalisland.engine;

import static io.tidalisland.config.Config.FPS;

/**
 * The game loop.
 */
public class GameLoop implements Runnable {
  private GamePanel gamePanel;
  private Thread gameThread;
  private boolean running;

  public GameLoop(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  /**
   * Starts the game loop.
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
   * Stops the game loop.
   */
  public void stop() {
    running = false;
    try {
      gameThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    double interval = 1_000_000_000 / FPS; // nanoseconds per frame
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    while (running) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / interval;
      lastTime = currentTime;

      if (delta >= 1) {
        gamePanel.update();
        gamePanel.repaint();
        gamePanel.endFrame();
        delta--;
      }

    }
  }
}

