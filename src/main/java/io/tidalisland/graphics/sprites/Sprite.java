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
  private boolean looping = true;
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
   * @param looping whether the animation should loop
   */
  public Sprite(List<SpriteFrame> frames, boolean looping) {
    this.frames = frames;
    this.looping = looping;
    animated = frames.size() > 1;
    lastTime = System.currentTimeMillis();
  }

  /**
   * Creates a new sprite with multiple frames with looping enabled.
   *
   * @param frames the frames
   */
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
