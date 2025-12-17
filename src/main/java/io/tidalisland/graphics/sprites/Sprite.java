package io.tidalisland.graphics.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A sprite with list of {@link SpriteFrame}s.
 */
public class Sprite {

  private final List<SpriteFrame> frames;
  private boolean animated = false;
  private boolean playing = true;

  private int repeat = -1; // -1 = infinite
  private int repeatsDone = 0;

  private double speedMultiplier = 1.0;
  private int frameIdx = 0;
  private long lastTime;

  /**
   * Creates a new sprite with a single frame.
   *
   * @param frame the frame
   */
  public Sprite(SpriteFrame frame) {
    frames = List.of(frame);
    animated = false;
  }

  /**
   * Creates a new sprite with a single frame from a path.
   *
   * @param path the path
   */
  public Sprite(String path) {
    this(new SpriteFrame(path));
  }

  /**
   * Creates a new sprite with multiple frames.
   *
   * @param frames the frames
   * @param repeat how many times to repeat the animation (-1 = infinite)
   */
  public Sprite(List<SpriteFrame> frames, int repeat) {
    this.frames = frames;
    this.repeat = repeat;
    animated = frames.size() > 1;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Creates a new sprite with multiple frames with looping enabled.
   *
   * @param frames the frames
   */
  public Sprite(List<SpriteFrame> frames) {
    this(frames, -1);
  }

  /**
   * Starts the animation.
   */
  public void play() {
    playing = true;
  }

  /**
   * Stops the animation.
   */
  public void stop() {
    playing = false;
  }

  /**
   * Resets the animation.
   */
  public void reset() {
    frameIdx = 0;
    repeatsDone = 0;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Plays the animation from the beginning.
   */
  public void playFromStart() {
    reset();
    play();
  }

  /**
   * Checks if the animation is finished.
   */
  public boolean isFinished() {
    if (!animated) {
      return true;
    }
    if (repeat < 0) {
      return false;
    }
    return !playing && frameIdx == frames.size() - 1;
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
        frameIdx = 0;
        repeatsDone++;

        if (repeat >= 0 && repeatsDone >= repeat) {
          frameIdx = frames.size() - 1;
          playing = false;
          break;
        }
      }

      current = frames.get(frameIdx);
      lastTime = now - delta;
    }
  }

  public boolean isAnimated() {
    return animated;
  }

  public void setSpeedMultiplier(double multiplier) {
    this.speedMultiplier = multiplier;
  }

  /** Returns the current frame. */
  public SpriteFrame getFrame() {
    if (frames == null || frames.isEmpty()) {
      return null;
    }
    return frames.get(frameIdx);
  }

  /** Returns the image of the current frame. */
  public BufferedImage getImage() {
    return getFrame().getImage();
  }
}
