package io.tidalisland.graphics.sprites;

import io.tidalisland.config.Config;
import io.tidalisland.utils.Position;
import io.tidalisland.utils.Size;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * A frame of a sprite.
 */
public class SpriteFrame {
  private BufferedImage image;
  private Size size;
  private int duration;
  private boolean flipX;
  private boolean flipY;

  /**
   * Creates a new sprite frame from an image.
   */
  public SpriteFrame(BufferedImage image, int duration) {
    this.image = image;
    this.duration = duration;
    size = new Size(
        image.getWidth() * Config.pixelScale(), image.getHeight() * Config.pixelScale());
  }

  public SpriteFrame(BufferedImage image) {
    this(image, 0);
  }

  /**
   * Creates a new sprite frame from a path.
   */
  public SpriteFrame(String path, int duration) {
    try {
      this.image = ImageIO.read(SpriteFrame.class.getResourceAsStream(path));
      this.duration = duration;
      size = new Size(
          image.getWidth() * Config.pixelScale(), image.getHeight() * Config.pixelScale());
    } catch (Exception e) {
      throw new RuntimeException("Failed to load sprite: " + path + " " + e.getMessage(), e);
    }
  }

  public SpriteFrame(String path) {
    this(path, 0);
  }

  /**
   * Draws the frame.
   */
  public void draw(Graphics g, Position position) {
    int x = position.getX();
    int y = position.getY();
    int w = size.getWidth();
    int h = size.getHeight();

    // Flip horizontally
    if (flipX) {
      x += w;
      w = -w;
    }
    // Flip vertically
    if (flipY) {
      y += h;
      h = -h;
    }

    g.drawImage(image, x, y, w, h, null);
  }

  /**
   * Draws the frame scaled to a given width and height.
   */
  public void drawScaled(Graphics g, int x, int y, int width, int height) {
    int drawX = x;
    int drawY = y;
    int drawW = width;
    int drawH = height;

    // Flip horizontally
    if (flipX) {
      drawX += drawW;
      drawW = -drawW;
    }

    // Flip vertically
    if (flipY) {
      drawY += drawH;
      drawH = -drawH;
    }

    g.drawImage(image, drawX, drawY, drawW, drawH, null);
  }

  public BufferedImage getImage() {
    return image;
  }

  public Size getSize() {
    return size;
  }

  public int getDuration() {
    return duration;
  }

  public boolean isFlipX() {
    return flipX;
  }

  public boolean isFlipY() {
    return flipY;
  }

  public void setFlipX(boolean flipX) {
    this.flipX = flipX;
  }

  public void setFlipY(boolean flipY) {
    this.flipY = flipY;
  }
}
