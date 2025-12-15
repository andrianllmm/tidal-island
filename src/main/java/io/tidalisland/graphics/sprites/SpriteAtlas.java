package io.tidalisland.graphics.sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * A sprite atlas.
 */
public class SpriteAtlas {

  private BufferedImage atlas;

  /**
   * Creates a new sprite atlas.
   *
   * @param path the path to the atlas image
   */
  public SpriteAtlas(String path) {
    try {
      atlas = ImageIO.read(getClass().getResourceAsStream(path));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to load atlas: " + path + " " + e.getMessage(), e);
    }
  }

  /**
   * Gets a specific frame from the atlas.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   * @param width the width
   * @param height the height
   * @return the frame
   */
  public BufferedImage getFrame(int x, int y, int width, int height) {
    return atlas.getSubimage(x, y, width, height);
  }

  /**
   * Gets a number of frames from the atlas in a given direction.
   *
   * @param startX the start x-coordinate
   * @param startY the start y-coordinate
   * @param frameWidth the frame width
   * @param frameHeight the frame height
   * @param direction the direction to get frames in
   * @param count the number of frames to get, or -1 for auto-calculate
   */
  public BufferedImage[] getFrames(int startX, int startY, int frameWidth, int frameHeight,
      Direction direction, int count) {
    ArrayList<BufferedImage> frames = new ArrayList<>();

    int maxFrames;
    if (count > 0) {
      maxFrames = count;
    } else {
      // Auto-calculate number of frames until end of atlas in the chosen direction
      if (direction == Direction.HORIZONTAL) {
        maxFrames = (atlas.getWidth() - startX) / frameWidth;
      } else { // VERTICAL
        maxFrames = (atlas.getHeight() - startY) / frameHeight;
      }
    }

    for (int i = 0; i < maxFrames; i++) {
      int x = startX;
      int y = startY;

      if (direction == Direction.HORIZONTAL) {
        x += i * frameWidth;
      } else {
        y += i * frameHeight;
      }

      frames.add(getFrame(x, y, frameWidth, frameHeight));
    }

    return frames.toArray(new BufferedImage[0]);
  }

  /**
   * Gets a number of frames from the atlas in a given direction.
   *
   * @param startX the start x-coordinate
   * @param startY the start y-coordinate
   * @param frameWidth the frame width
   * @param frameHeight the frame height
   * @param direction the direction to get frames in
   */
  public BufferedImage[] getFrames(int startX, int startY, int frameWidth, int frameHeight,
      Direction direction) {
    return getFrames(startX, startY, frameWidth, frameHeight, direction, -1);
  }

  /** A frame of an atlas. */
  public static class AtlasFrame {
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    /** Creates a new atlas frame. */
    public AtlasFrame(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
    }
  }

  /** Direction of the atlas. */
  public enum Direction {
    HORIZONTAL, VERTICAL
  }
}
