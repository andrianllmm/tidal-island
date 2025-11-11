package io.tidalisland.graphics;

import java.util.List;

/**
 * A sprite with list of frames.
 */
public class Sprite {
  private final List<SpriteFrame> frames;
  private boolean animated = false;
  private boolean playing = true;
  private boolean looping = true;
  private double speedMultiplier = 1.0;
  private int frameIdx = 0;
  private long lastTime;

  /**
   * Creates a new sprite with a single frame.
   */
  public Sprite(SpriteFrame frame) {
    frames = List.of(frame);
    animated = false;
  }

  public Sprite(String path) {
    this(new SpriteFrame(path));
  }

  /**
   * Creates a new sprite with multiple frames.
   */
  public Sprite(List<SpriteFrame> frames, boolean looping) {
    this.frames = frames;
    this.looping = looping;
    animated = frames.size() > 1;
    lastTime = System.currentTimeMillis();
  }

  public Sprite(List<SpriteFrame> frames) {
    this(frames, true);
  }

  public void play() {
    playing = true;
  }

  public void stop() {
    playing = false;
  }

  public void reset() {
    frameIdx = 0;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Updates the sprite.
   */
  public void update() {
    if (!animated || !playing || frames == null || frames.size() <= 1) {
      return;
    }

    SpriteFrame current = frames.get(frameIdx);
    long now = System.currentTimeMillis();
    long delta = now - lastTime;

    long frameDuration = (long) (current.getDuration() / speedMultiplier);
    while (delta > frameDuration) {
      delta -= current.getDuration() / speedMultiplier;
      frameIdx++;

      if (frameIdx >= frames.size()) {
        if (looping) {
          frameIdx = 0;
        } else {
          frameIdx = frames.size() - 1;
          playing = false;
          break;
        }
      }

      current = frames.get(frameIdx);
      lastTime = now - delta;
    }
  }

  /**
   * Gets the current frame.
   */
  public SpriteFrame getFrame() {
    if (frames == null || frames.isEmpty()) {
      return null;
    }
    return frames.get(frameIdx);
  }

  public boolean isAnimated() {
    return animated;
  }

  public void setSpeedMultiplier(double multiplier) {
    this.speedMultiplier = multiplier;
  }
}
