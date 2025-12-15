package io.tidalisland.engine;

/**
 * A singleton GameClock that provides game-time delta for all systems.
 */
public class GameClock {

  private static final GameClock instance = new GameClock();

  /**
   * Get the singleton instance.
   */
  public static GameClock getInstance() {
    return instance;
  }

  private long deltaMillis; // time elapsed this frame
  private long totalElapsed; // total time elapsed
  private long lastUpdateTime; // real time of last update
  private boolean paused; // pause flag

  private GameClock() {
    deltaMillis = 0;
    lastUpdateTime = System.nanoTime();
    paused = false;
  }

  /**
   * Computes the delta time since the last frame and updates internal state.
   */
  public void update() {
    long now = System.nanoTime();
    long delta = (now - lastUpdateTime) / 1_000_000; // convert ns -> ms
    lastUpdateTime = now;

    deltaMillis = paused ? 0 : delta;

    if (!paused) {
      totalElapsed += deltaMillis;
    }
  }

  /**
   * Gets the delta time for the current frame.
   *
   * @return the delta time (in milliseconds)
   */
  public long getDeltaMillis() {
    return deltaMillis;
  }

  /**
   * Gets the total elapsed time.
   *
   * @return the total elapsed time (in milliseconds)
   */
  public long getTotalElapsedMillis() {
    return totalElapsed;
  }

  /**
   * Pause or unpause the game clock.
   */
  public void setPaused(boolean paused) {
    this.paused = paused;

    // Reset lastUpdateTime to prevent a big delta when resuming
    if (!paused) {
      lastUpdateTime = System.nanoTime();
    }
  }

  /**
   * Gets whether the game is currently paused.
   *
   * @return true if the game is paused
   */
  public boolean isPaused() {
    return paused;
  }

  /**
   * Resets the clock to default values.
   */
  public void reset() {
    deltaMillis = 0;
    lastUpdateTime = System.nanoTime();
    paused = false;
  }
}
